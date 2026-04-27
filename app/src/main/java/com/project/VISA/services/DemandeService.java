package com.project.VISA.services;

import com.project.VISA.dtos.DemandeDTO;
import com.project.VISA.dtos.DemandePieceStatusDTO;
import com.project.VISA.models.*;
import com.project.VISA.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DemandeService {

    @Autowired private DemandeRepository demandeRepository;
    @Autowired private DemandeurRepository demandeurRepository;
    @Autowired private EtatCivilRepository etatCivilRepository;
    @Autowired private PasseportRepository passeportRepository;
    @Autowired private TypeDemandeRepository typeDemandeRepository;
    @Autowired private StatusDmRepository statusDmRepository;
    @Autowired private NationaliteRepository nationaliteRepository;
    @Autowired private SituationFamRepository situationFamRepository;
    @Autowired private PieceRepository pieceRepository;
    @Autowired private TypeVisaRepository typeVisaRepository;
    @Autowired private VisaRepository visaRepository;
    @Autowired private DemandePieceRepository demandePieceRepository;
    @Autowired private PieceDemandeRepository pieceDemandeRepository;
    @Autowired private CategoriePieceRepository categoriePieceRepository;

    // ─── CREATE ───────────────────────────────────────────────────────────────

    @Transactional
    public Demande creerNouvelleDemande(DemandeDTO dto) {
        // 1. Créer d'abord l'EtatCivil
        EtatCivil etatCivil = new EtatCivil();
        etatCivil.setNom(dto.getLastName());
        etatCivil.setPrenoms(dto.getFirstNames());
        etatCivil.setNomJeuneFille(dto.getMaidenName());
        etatCivil.setDateNaissance(dto.getBirthDate());
        etatCivil.setSituationFamille(dto.getMaritalStatus());
        etatCivil.setNationalite(dto.getNationality());
        etatCivil.setDomicileHabituel(dto.getHomeAddress());
        etatCivil.setProfession(dto.getOccupation());
        etatCivil.setEmployeur(dto.getEmployerName());
        etatCivil.setAdresseEmployeur(dto.getEmployerAddress());
        etatCivil = etatCivilRepository.save(etatCivil);

        // 2. Gérer le Demandeur (lié à EtatCivil)
        Demandeur demandeur;

        if (dto.getIdDemandeur() != null) {
            demandeur = demandeurRepository.findById(dto.getIdDemandeur())
                    .orElseThrow(() -> new RuntimeException("Demandeur non trouvé avec l'id : " + dto.getIdDemandeur()));
        } else {
            demandeur = new Demandeur();
            demandeur.setEtatCivil(etatCivil);
            demandeur.setNom(dto.getLastName());
            demandeur.setPrenom(dto.getFirstNames());
            demandeur.setDateNaissance(dto.getBirthDate());

            Nationalite nat = nationaliteRepository.findAll().stream()
                    .filter(n -> n.getNom().equalsIgnoreCase(dto.getNationality()))
                    .findFirst().orElse(null);
            demandeur.setNationalite(nat);

            SituationFam sit = situationFamRepository.findAll().stream()
                    .filter(s -> s.getLibelle().equalsIgnoreCase(dto.getMaritalStatus()))
                    .findFirst().orElse(null);
            demandeur.setSituationFamille(sit);

            Piece p = pieceRepository.findAll().stream().findFirst().orElse(null);
            demandeur.setPiecePrincipale(p);

            demandeur = demandeurRepository.save(demandeur);

            // Mettre à jour EtatCivil avec le Demandeur
            etatCivil.setDemandeur(demandeur);
            etatCivilRepository.save(etatCivil);
        }

        // 3. Gérer le Passeport
        Passeport passeport = new Passeport();
        passeport.setDemandeur(demandeur);
        passeportRepository.save(passeport);

        // 4. Gérer la Demande
        Demande demande = new Demande();
        demande.setDemandeur(demandeur);

        TypeDemande type = typeDemandeRepository.findAll().stream()
                .filter(t -> t.getNom().equalsIgnoreCase(dto.getTypeDemande()))
                .findFirst().orElseThrow(() -> new RuntimeException("Type de demande inconnu : " + dto.getTypeDemande()));
        demande.setTypeDemande(type);

        // Statut Initial "CREE" (fallback si absent)
        StatusDm status = statusDmRepository.findAll().stream()
                .filter(s -> "CREE".equalsIgnoreCase(s.getStatus()))
                .findFirst()
                .orElse(null);
        if (status == null) {
            List<StatusDm> allStatus = statusDmRepository.findAll();
            if (!allStatus.isEmpty()) {
                status = allStatus.get(0);
            } else {
                StatusDm created = new StatusDm();
                created.setStatus("CREE");
                created.setObservation("Demande creee");
                status = statusDmRepository.save(created);
            }
        }
        demande.setStatus(status);

        demande = demandeRepository.save(demande);

        // 5. Initialiser les pièces requises pour ce type de demande
        initializePiecesForDemande(demande);

        // 6. Déclencher le Vérificateur (Simple constat par défaut pour ce sprint)
        declencherVerificateur(demande, dto);

        return demandeRepository.save(demande);
    }

    private void declencherVerificateur(Demande demande, DemandeDTO dto) {
        StringBuilder constat = new StringBuilder();
        
        // Exemple de logique de vérification simple basée sur les booleans du DTO
        if (!dto.isaFourniPhotos()) constat.append("Manque Photos; ");
        if (!dto.isaFourniCopiePasseport()) constat.append("Manque Copie Passeport; ");
        
        if (constat.length() > 0) {
            StatusDm currentStatus = demande.getStatus();
            if (currentStatus != null) {
                currentStatus.setObservation("Constat : " + constat.toString());
                statusDmRepository.save(currentStatus);
            }
        }
    }

    // ─── READ ─────────────────────────────────────────────────────────────────

    public List<Demande> findAll() {
        return demandeRepository.findAll();
    }

    public Optional<Demande> findById(Long id) {
        return demandeRepository.findById(id);
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────

    @Transactional
    public Optional<Demande> update(Long id, DemandeDTO dto) {
        return demandeRepository.findById(id).map(existing -> {
            Demandeur demandeur = existing.getDemandeur();
            EtatCivil etatCivil = demandeur.getEtatCivil();

            // Mettre à jour le Demandeur
            if (dto.getLastName() != null) demandeur.setNom(dto.getLastName());
            if (dto.getFirstNames() != null) demandeur.setPrenom(dto.getFirstNames());
            if (dto.getBirthDate() != null) demandeur.setDateNaissance(dto.getBirthDate());

            if (dto.getNationality() != null) {
                Nationalite nat = nationaliteRepository.findAll().stream()
                        .filter(n -> n.getNom().equalsIgnoreCase(dto.getNationality()))
                        .findFirst().orElse(null);
                demandeur.setNationalite(nat);
            }

            if (dto.getMaritalStatus() != null) {
                SituationFam sit = situationFamRepository.findAll().stream()
                        .filter(s -> s.getLibelle().equalsIgnoreCase(dto.getMaritalStatus()))
                        .findFirst().orElse(null);
                demandeur.setSituationFamille(sit);
            }

            demandeurRepository.save(demandeur);

            // Mettre à jour EtatCivil aussi
            if (etatCivil != null) {
                if (dto.getLastName() != null) etatCivil.setNom(dto.getLastName());
                if (dto.getFirstNames() != null) etatCivil.setPrenoms(dto.getFirstNames());
                if (dto.getMaidenName() != null) etatCivil.setNomJeuneFille(dto.getMaidenName());
                if (dto.getBirthDate() != null) etatCivil.setDateNaissance(dto.getBirthDate());
                if (dto.getMaritalStatus() != null) etatCivil.setSituationFamille(dto.getMaritalStatus());
                if (dto.getNationality() != null) etatCivil.setNationalite(dto.getNationality());
                if (dto.getHomeAddress() != null) etatCivil.setDomicileHabituel(dto.getHomeAddress());
                if (dto.getOccupation() != null) etatCivil.setProfession(dto.getOccupation());
                if (dto.getEmployerName() != null) etatCivil.setEmployeur(dto.getEmployerName());
                if (dto.getEmployerAddress() != null) etatCivil.setAdresseEmployeur(dto.getEmployerAddress());
                etatCivilRepository.save(etatCivil);
            }

            // Mettre à jour le Type de Demande si fourni
            if (dto.getTypeDemande() != null) {
                TypeDemande type = typeDemandeRepository.findAll().stream()
                        .filter(t -> t.getNom().equalsIgnoreCase(dto.getTypeDemande()))
                        .findFirst().orElse(null);
                if (type != null) {
                    existing.setTypeDemande(type);
                }
            }

            // Re-déclencher la vérification
            declencherVerificateur(existing, dto);

            return demandeRepository.save(existing);
        });
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────

    @Transactional
    public boolean deleteById(Long id) {
        if (demandeRepository.existsById(id)) {
            demandeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ─── PIECES ────────────────────────────────────────────────────────────────

    /**
     * Ajouter/initialiser les pièces requises pour une demande selon son type
     */
    @Transactional
    public void initializePiecesForDemande(Demande demande) {
        TypeDemande typeDemande = demande.getTypeDemande();

        // Récupérer les pièces requises pour ce type de demande
        List<PieceDemande> requiredPieces = pieceDemandeRepository.findByTypeDmId(typeDemande.getId());

        if (requiredPieces.isEmpty()) {
            categoriePieceRepository.findAll().forEach(cat -> {
                DemandePiece demandePiece = new DemandePiece(demande, cat);
                demande.addPiece(demandePiece);
            });
            demandeRepository.save(demande);
            return;
        }

        for (PieceDemande pieceDemande : requiredPieces) {
            // Vérifier si la pièce n'existe pas déjà
            boolean exists = demande.getPieces().stream()
                    .anyMatch(dp -> dp.getCategoriePiece().getId().equals(pieceDemande.getCategoriePiece().getId()));

            if (!exists) {
                DemandePiece demandePiece = new DemandePiece(demande, pieceDemande.getCategoriePiece());
                demande.addPiece(demandePiece);
            }
        }

        demandeRepository.save(demande);
    }

    /**
     * Mettre à jour le statut de plusieurs pièces en une seule transaction
     */
    @Transactional
    public void updateMultiplePiecesStatus(Long demandeId, List<DemandePieceStatusDTO> pieceDtos) {
        Demande demande = demandeRepository.findById(demandeId)
            .orElseThrow(() -> new RuntimeException("Demande non trouvée"));

        if (pieceDtos != null) {
            for (DemandePieceStatusDTO dto : pieceDtos) {
                if (dto.getCategoriePieceId() != null && dto.getIsProvided() != null) {
                    DemandePiece demandePiece = demande.getPieces().stream()
                        .filter(dp -> dp.getCategoriePiece() != null
                            && dto.getCategoriePieceId().equals(dp.getCategoriePiece().getId()))
                        .findFirst()
                        .orElse(null);

                    if (demandePiece == null) {
                        CategoriePiece categoriePiece = categoriePieceRepository.findById(dto.getCategoriePieceId())
                            .orElseThrow(() -> new RuntimeException("Catégorie de pièce non trouvée"));
                        demandePiece = new DemandePiece(demande, categoriePiece);
                        demande.addPiece(demandePiece);
                    }
                    demandePiece.setIsProvided(dto.getIsProvided());
                }
            }
        }
        demandeRepository.save(demande);
    }

    /**
     * Mettre à jour le statut d'une pièce pour une demande
     */
    @Transactional
    public DemandePiece updatePieceStatus(Long demandeId, Long categoriePieceId, Boolean isProvided) {
        DemandePiece demandePiece = demandePieceRepository
            .findByDemandeIdAndCategoriePieceId(demandeId, categoriePieceId)
            .orElse(null);

        if (demandePiece == null) {
            Demande demande = demandeRepository.findById(demandeId)
                .orElseThrow(() -> new RuntimeException("Demande non trouvée"));

            // Evite de creer un doublon si la piece est deja attachee a la demande
            demandePiece = demande.getPieces().stream()
                .filter(dp -> dp.getCategoriePiece() != null
                    && categoriePieceId.equals(dp.getCategoriePiece().getId()))
                .findFirst()
                .orElse(null);

            if (demandePiece == null) {
            CategoriePiece categoriePiece = categoriePieceRepository.findById(categoriePieceId)
                .orElseThrow(() -> new RuntimeException("Catégorie de pièce non trouvée"));

            DemandePiece newDemandePiece = new DemandePiece(demande, categoriePiece);
            demande.addPiece(newDemandePiece);
            demandePiece = newDemandePiece;
            }
        }

        demandePiece.setIsProvided(isProvided);
        return demandePieceRepository.save(demandePiece);
    }

    /**
     * Récupérer toutes les pièces d'une demande
     */
    public List<DemandePiece> getPiecesForDemande(Long demandeId) {
        return demandePieceRepository.findByDemandeId(demandeId);
    }

    /**
     * Récupérer le passeport d'un demandeur
     */
    public Optional<Passeport> getPasseportForDemandeur(Long demandeurId) {
        return passeportRepository.findByDemandeurId(demandeurId).stream().findFirst();
    }
}
