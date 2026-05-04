package com.project.VISA.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.VISA.dtos.DemandeRequest;
import com.project.VISA.dtos.DemandeResponse;
import com.project.VISA.dtos.DemandeValidationResponse;
import com.project.VISA.models.Demande;
import com.project.VISA.models.TypeVisa;
import com.project.VISA.repositories.CarteResidentRepository;
import com.project.VISA.repositories.DemandeRepository;
import com.project.VISA.repositories.EtatCivilRepository;
import com.project.VISA.repositories.PasseportRepository;
import com.project.VISA.repositories.PieceDemandeRepository;
import com.project.VISA.repositories.PieceRepository;
import com.project.VISA.repositories.StatusDmRepository;
import com.project.VISA.repositories.TypeDemandeObjetMetierObligatoireRepository;
import com.project.VISA.repositories.TypeDemandeRepository;
import com.project.VISA.repositories.TypeVisaRepository;
import com.project.VISA.repositories.VisaRepository;
import com.project.VISA.repositories.VisaTransformableRepository;

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

    public DemandeService(
            DemandeRepository demandeRepository,
            DemandeurService demandeurService,
            TypeDemandeRepository typeDemandeRepository,
            StatusDmRepository statusDmRepository,
            TypeVisaRepository typeVisaRepository,
            PieceDemandeRepository pieceDemandeRepository,
            TypeDemandeObjetMetierObligatoireRepository objetMetierObligatoireRepository,
            PieceRepository pieceRepository,
            PasseportRepository passeportRepository,
            VisaRepository visaRepository,
            VisaTransformableRepository visaTransformableRepository,
            EtatCivilRepository etatCivilRepository,
            CarteResidentRepository carteResidentRepository) {
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
        demandeRepository.delete(findEntity(id));
    }

    public DemandeValidationResponse validate(Long demandeId) {
        Demande demande = findEntity(demandeId);
        Long typeDemandeId = demande.getTypeDemande().getId();
        Long demandeurId = demande.getDemandeur().getId();

        List<String> piecesManquantes = new ArrayList<>();
        pieceDemandeRepository.findByTypeDemandeId(typeDemandeId).forEach(rule -> {
            boolean hasValidPiece = pieceRepository.existsByDemandeurIdAndCategoriePieceIdAndValideTrue(
                    demandeurId,
                    rule.getCategoriePiece().getId());
            if (!hasValidPiece) {
                piecesManquantes.add(rule.getCategoriePiece().getLibelle());
            }
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
            if (!present) {
                objetsManquants.add(objet);
            }
        });

        DemandeValidationResponse response = new DemandeValidationResponse();
        response.setDemandeId(demande.getId());
        response.setPiecesManquantes(piecesManquantes);
        response.setObjetsManquants(objetsManquants);
        response.setValide(piecesManquantes.isEmpty() && objetsManquants.isEmpty());
        return response;
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
        DemandeResponse response = new DemandeResponse();
        response.setId(demande.getId());
        response.setCreatedAt(demande.getCreatedAt());
        response.setUpdatedAt(demande.getUpdatedAt());
        response.setDemandeurId(demande.getDemandeur().getId());
        response.setNomDemandeur(demande.getDemandeur().getNom() + " " + demande.getDemandeur().getPrenom());
        response.setStatusId(demande.getStatus().getId());
        response.setStatus(demande.getStatus().getCode());
        response.setTypeDemandeId(demande.getTypeDemande().getId());
        response.setTypeDemande(demande.getTypeDemande().getNom());
        if (demande.getTypeVisa() != null) {
            response.setTypeVisaId(demande.getTypeVisa().getId());
            response.setTypeVisa(demande.getTypeVisa().getLibelle());
        }
        return response;
    }
}
