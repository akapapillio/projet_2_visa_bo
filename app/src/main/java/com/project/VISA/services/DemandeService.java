package com.project.VISA.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.VISA.dtos.DemandeRequest;
import com.project.VISA.dtos.DemandeResponse;
import com.project.VISA.dtos.DemandeValidationResponse;
import com.project.VISA.dtos.FileUploadResponse;
import com.project.VISA.models.Demande;
import com.project.VISA.models.StatusDm;
import com.project.VISA.models.TypeVisa;
import com.project.VISA.repositories.*;

@Service
public class DemandeService {

    private final DemandeRepository demandeRepository;
    private final DemandeurService demandeurService;
    private final TypeDemandeRepository typeDemandeRepository;
    private final StatusDmRepository statusDmRepository;
    private final TypeVisaRepository typeVisaRepository;
    private final PieceDemandeRepository pieceDemandeRepository;
    private final TypeDemandeObjetMetierObligatoireRepository objetMetierObligatoireRepository;
    private final PieceRepository pieceRepository;
    private final PasseportRepository passeportRepository;
    private final VisaRepository visaRepository;
    private final VisaTransformableRepository visaTransformableRepository;
    private final EtatCivilRepository etatCivilRepository;
    private final CarteResidentRepository carteResidentRepository;
    private final FileStorageService fileStorageService;

    private static final Map<String, Set<String>> ALLOWED_TRANSITIONS = Map.of(
            "DEMANDE_CREE", Set.of("PHOTO_SIGNATURE_COMPLETE", "REFUSEE"),
            "PHOTO_SIGNATURE_COMPLETE", Set.of("EN_COURS_DE_TRAITEMENT", "REFUSEE"),
            "EN_COURS_DE_TRAITEMENT", Set.of("VALIDEE", "REFUSEE"),
            "VALIDEE", Set.of("REFUSEE"),
            "REFUSEE", Set.of("DEMANDE_CREE")
    );

    public DemandeService(
            DemandeRepository demandeRepository, DemandeurService demandeurService,
            TypeDemandeRepository typeDemandeRepository, StatusDmRepository statusDmRepository,
            TypeVisaRepository typeVisaRepository, PieceDemandeRepository pieceDemandeRepository,
            TypeDemandeObjetMetierObligatoireRepository objetMetierObligatoireRepository,
            PieceRepository pieceRepository, PasseportRepository passeportRepository,
            VisaRepository visaRepository, VisaTransformableRepository visaTransformableRepository,
            EtatCivilRepository etatCivilRepository, CarteResidentRepository carteResidentRepository,
            FileStorageService fileStorageService) {
        this.demandeRepository = demandeRepository;
        this.demandeurService = demandeurService;
        this.typeDemandeRepository = typeDemandeRepository;
        this.statusDmRepository = statusDmRepository;
        this.typeVisaRepository = typeVisaRepository;
        this.pieceDemandeRepository = pieceDemandeRepository;
        this.objetMetierObligatoireRepository = objetMetierObligatoireRepository;
        this.pieceRepository = pieceRepository;
        this.passeportRepository = passeportRepository;
        this.visaRepository = visaRepository;
        this.visaTransformableRepository = visaTransformableRepository;
        this.etatCivilRepository = etatCivilRepository;
        this.carteResidentRepository = carteResidentRepository;
        this.fileStorageService = fileStorageService;
    }

    public List<DemandeResponse> findAll() {
        return demandeRepository.findAll().stream().map(this::toResponse).toList();
    }

    public List<DemandeResponse> findByDemandeur(Long demandeurId) {
        return demandeRepository.findByDemandeurId(demandeurId).stream().map(this::toResponse).toList();
    }

    public DemandeResponse findById(Long id) {
        return toResponse(findEntity(id));
    }

    public DemandeResponse create(DemandeRequest request) {
        Demande demande = new Demande();
        applyRequest(request, demande, true);
        return toResponse(demandeRepository.save(demande));
    }

    public DemandeResponse update(Long id, DemandeRequest request) {
        Demande demande = findEntity(id);
        applyRequest(request, demande, false);
        return toResponse(demandeRepository.save(demande));
    }

    public void delete(Long id) {
        Demande demande = findEntity(id);
        fileStorageService.deleteFile(demande.getPhotoPath());
        fileStorageService.deleteFile(demande.getSignaturePath());
        demandeRepository.delete(demande);
    }

    /** Upload la photo pour une demande. */
    public FileUploadResponse uploadPhoto(Long demandeId, MultipartFile file) {
        Demande demande = findEntity(demandeId);
        fileStorageService.deleteFile(demande.getPhotoPath());

        String path = fileStorageService.storeFile(file, "photos", demandeId);
        String url = fileStorageService.buildFileUrl(path);
        LocalDateTime now = LocalDateTime.now();

        demande.setPhotoPath(path);
        demande.setPhotoUrl(url);
        demande.setPhotoUploadDate(now);
        demandeRepository.save(demande);

        return buildFileResponse(demandeId, path, url, "photo", now);
    }

    /** Upload la signature pour une demande. */
    public FileUploadResponse uploadSignature(Long demandeId, MultipartFile file) {
        Demande demande = findEntity(demandeId);
        fileStorageService.deleteFile(demande.getSignaturePath());

        String path = fileStorageService.storeFile(file, "signatures", demandeId);
        String url = fileStorageService.buildFileUrl(path);
        LocalDateTime now = LocalDateTime.now();

        demande.setSignaturePath(path);
        demande.setSignatureUrl(url);
        demande.setSignatureUploadDate(now);
        demandeRepository.save(demande);

        return buildFileResponse(demandeId, path, url, "signature", now);
    }

    /** Met à jour le statut avec validation de transition et auto-refus. */
    public DemandeResponse updateStatus(Long demandeId, String targetStatusCode) {
        Demande demande = findEntity(demandeId);
        String current = demande.getStatus().getCode();

        Set<String> allowed = ALLOWED_TRANSITIONS.getOrDefault(current, Set.of());
        if (!allowed.contains(targetStatusCode)) {
            throw new BusinessValidationException(
                    "Transition non autorisée de '" + current + "' vers '" + targetStatusCode
                            + "'. Transitions possibles: " + allowed);
        }

        if ("PHOTO_SIGNATURE_COMPLETE".equals(targetStatusCode)) {
            boolean hasPhoto = demande.getPhotoPath() != null && !demande.getPhotoPath().isBlank();
            boolean hasSig = demande.getSignaturePath() != null && !demande.getSignaturePath().isBlank();

            if (!hasPhoto || !hasSig) {
                StatusDm refused = statusDmRepository.findByCode("REFUSEE")
                        .orElseThrow(() -> new BusinessValidationException("Status REFUSEE absent en base."));
                demande.setStatus(refused);
                String raison = (!hasPhoto && !hasSig) ? "Photo et signature non fournies"
                        : !hasPhoto ? "Photo non fournie" : "Signature non fournie";
                demande.setRaisonRefus(raison);
                demandeRepository.save(demande);
                throw new BusinessValidationException("Demande refusée: " + raison);
            }
        }

        StatusDm target = statusDmRepository.findByCode(targetStatusCode)
                .orElseThrow(() -> new ResourceNotFoundException("Status introuvable: " + targetStatusCode));
        demande.setStatus(target);

        if ("REFUSEE".equals(targetStatusCode) && demande.getRaisonRefus() == null) {
            demande.setRaisonRefus("Demande refusée manuellement");
        }
        if (!"REFUSEE".equals(targetStatusCode)) {
            demande.setRaisonRefus(null);
        }

        return toResponse(demandeRepository.save(demande));
    }

    public DemandeValidationResponse validate(Long demandeId) {
        Demande demande = findEntity(demandeId);
        Long typeDemandeId = demande.getTypeDemande().getId();
        Long demandeurId = demande.getDemandeur().getId();

        List<String> piecesManquantes = new ArrayList<>();
        pieceDemandeRepository.findByTypeDemandeId(typeDemandeId).forEach(rule -> {
            boolean ok = pieceRepository.existsByDemandeurIdAndCategoriePieceIdAndValideTrue(
                    demandeurId, rule.getCategoriePiece().getId());
            if (!ok) piecesManquantes.add(rule.getCategoriePiece().getLibelle());
        });

        List<String> objetsManquants = new ArrayList<>();
        objetMetierObligatoireRepository.findByTypeDemandeIdAndObligatoireTrue(typeDemandeId).forEach(rule -> {
            String objet = rule.getTypeObjet().getNom();
            boolean present = switch (objet.toUpperCase()) {
                case "PASSEPORT" -> passeportRepository.existsByDemandeurId(demandeurId);
                case "VISA" -> visaRepository.existsByDemandeurId(demandeurId);
                case "VISA_TRANSFORMABLE" -> visaTransformableRepository.existsByDemandeurId(demandeurId);
                case "ETAT_CIVIL" -> etatCivilRepository.existsByDemandeurId(demandeurId);
                case "CARTE_RESIDENT" -> carteResidentRepository.existsByDemandeurId(demandeurId);
                default -> false;
            };
            if (!present) objetsManquants.add(objet);
        });

        if (demande.getPhotoPath() == null || demande.getPhotoPath().isBlank())
            piecesManquantes.add("Photo d'identité");
        if (demande.getSignaturePath() == null || demande.getSignaturePath().isBlank())
            piecesManquantes.add("Signature");

        DemandeValidationResponse resp = new DemandeValidationResponse();
        resp.setDemandeId(demande.getId());
        resp.setPiecesManquantes(piecesManquantes);
        resp.setObjetsManquants(objetsManquants);
        resp.setValide(piecesManquantes.isEmpty() && objetsManquants.isEmpty());
        return resp;
    }

    public Demande findEntity(Long id) {
        return demandeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Demande introuvable: " + id));
    }

    private void applyRequest(DemandeRequest request, Demande demande, boolean createMode) {
        demande.setDemandeur(demandeurService.findEntity(request.getDemandeurId()));

        var typeDemande = typeDemandeRepository.findById(request.getTypeDemandeId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Type demande introuvable: " + request.getTypeDemandeId()));
        demande.setTypeDemande(typeDemande);

        boolean nouveauTitre = "NOUVEAU_TITRE".equalsIgnoreCase(typeDemande.getNom());
        if (nouveauTitre) {
            if (request.getTypeVisaId() == null) {
                throw new BusinessValidationException("Le type de visa est obligatoire pour un nouveau titre.");
            }
            TypeVisa typeVisa = typeVisaRepository.findById(request.getTypeVisaId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Type visa introuvable: " + request.getTypeVisaId()));
            demande.setTypeVisa(typeVisa);
        } else {
            demande.setTypeVisa(null);
        }

        if (request.getStatusId() != null) {
            var status = statusDmRepository.findById(request.getStatusId())
                    .orElseThrow(() -> new ResourceNotFoundException("Status introuvable: " + request.getStatusId()));
            demande.setStatus(status);
            return;
        }

        if (createMode) {
            var defaultStatus = statusDmRepository.findByCode("DEMANDE_CREE")
                    .orElseThrow(() -> new BusinessValidationException(
                            "Status DEMANDE_CREE absent en base. Ajouter les donnees de reference."));
            demande.setStatus(defaultStatus);
        }
    }

    private DemandeResponse toResponse(Demande demande) {
        DemandeResponse r = new DemandeResponse();
        r.setId(demande.getId());
        r.setCreatedAt(demande.getCreatedAt());
        r.setUpdatedAt(demande.getUpdatedAt());
        r.setDemandeurId(demande.getDemandeur().getId());
        r.setNomDemandeur(demande.getDemandeur().getNom() + " " + demande.getDemandeur().getPrenom());
        r.setStatusId(demande.getStatus().getId());
        r.setStatus(demande.getStatus().getCode());
        r.setTypeDemandeId(demande.getTypeDemande().getId());
        r.setTypeDemande(demande.getTypeDemande().getNom());
        if (demande.getTypeVisa() != null) {
            r.setTypeVisaId(demande.getTypeVisa().getId());
            r.setTypeVisa(demande.getTypeVisa().getLibelle());
        }
        r.setPhotoUrl(demande.getPhotoUrl());
        r.setPhotoUploadDate(demande.getPhotoUploadDate());
        r.setSignatureUrl(demande.getSignatureUrl());
        r.setSignatureUploadDate(demande.getSignatureUploadDate());
        r.setRaisonRefus(demande.getRaisonRefus());
        return r;
    }

    private FileUploadResponse buildFileResponse(Long demandeId, String path, String url, String type, LocalDateTime date) {
        FileUploadResponse r = new FileUploadResponse();
        r.setDemandeId(demandeId);
        r.setFilePath(path);
        r.setFileUrl(url);
        r.setType(type);
        r.setUploadDate(date);
        return r;
    }
}
