package com.project.VISA.controllers;

import com.project.VISA.dtos.ApiResponseDTO;
import com.project.VISA.dtos.DemandeDTO;
import com.project.VISA.dtos.DemandePieceStatusDTO;
import com.project.VISA.models.Demande;
import com.project.VISA.models.Demandeur;
import com.project.VISA.models.DemandePiece;
import com.project.VISA.models.EtatCivil;
import com.project.VISA.models.Visa;
import com.project.VISA.models.VisaTransformable;
import com.project.VISA.services.DemandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * API REST — Demande
 * Base URL : /api/visa/demandes
 *
 * POST   /api/visa/demandes                → Créer une nouvelle demande (avec EtatCivil + VisaTransformable)
 * GET    /api/visa/demandes                → Lister toutes les demandes
 * GET    /api/visa/demandes/{id}           → Obtenir une demande par ID
 * PUT    /api/visa/demandes/{id}           → Modifier une demande existante
 * DELETE /api/visa/demandes/{id}           → Supprimer une demande
 *
 * Endpoint legacy (alias) :
 * POST   /api/visa/demande_transformation  → Alias POST pour rétro-compatibilité FO
 */
@RestController
@RequestMapping("/api/visa")
@CrossOrigin(origins = "*")
public class DemandeApiController {

    @Autowired
    private DemandeService demandeService;

    // ─── CREATE ───────────────────────────────────────────────────────────────

    @PostMapping("/demandes")
    public ResponseEntity<?> creerDemande(@RequestBody DemandeDTO demandeDTO) {
        try {
            Demande demande = demandeService.creerNouvelleDemande(demandeDTO);
            return new ResponseEntity<>(demande, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la création : " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Alias rétro-compatible avec le FO (sprint-1 original)
     */
    @PostMapping("/demande_transformation")
    public ResponseEntity<?> creerDemandeTransformation(@RequestBody DemandeDTO demandeDTO) {
        return creerDemande(demandeDTO);
    }

    // ─── READ ALL ─────────────────────────────────────────────────────────────

    @GetMapping("/demandes")
    public ResponseEntity<?> getAllDemandes() {
        try {
            List<Demande> demandes = demandeService.findAll();
            List<?> response = demandes.stream().map(this::toDemandeDto).toList();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la récupération des demandes : " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ─── READ ONE ─────────────────────────────────────────────────────────────

    @GetMapping("/demandes/{id}")
    public ResponseEntity<?> getDemandeById(@PathVariable("id") Long id) {
        try {
            var result = demandeService.findById(id);
            if (result.isPresent()) {
                return ResponseEntity.ok(toDemandeDto(result.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Demande non trouvée avec l'id : " + id);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la récupération de la demande : " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────

    @PutMapping("/demandes/{id}")
    public ResponseEntity<?> updateDemande(@PathVariable("id") Long id,
                                           @RequestBody DemandeDTO demandeDTO) {
        try {
            var result = demandeService.update(id, demandeDTO);
            if (result.isPresent()) {
                return ResponseEntity.ok(result.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Demande non trouvée avec l'id : " + id);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la modification de la demande : " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────

    @DeleteMapping("/demandes/{id}")
    public ResponseEntity<?> deleteDemande(@PathVariable("id") Long id) {
        try {
            if (demandeService.deleteById(id)) {
                return ResponseEntity.ok(new ApiResponseDTO("Demande supprimée avec succès", true));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponseDTO("Demande non trouvée avec l'id : " + id, false));
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponseDTO("Erreur lors de la suppression de la demande : " + e.getMessage(), false),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ─── PIECES ─────────────────────────────────────────────────────────────

    @GetMapping("/demandes/{id}/pieces")
    public ResponseEntity<?> getPieces(@PathVariable("id") Long id) {
        try {
            Demande demande = demandeService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Demande non trouvée avec l'id : " + id));

            if (demande.getPieces() == null || demande.getPieces().isEmpty()) {
                demandeService.initializePiecesForDemande(demande);
            }

            List<DemandePiece> pieces = demandeService.getPiecesForDemande(id);
            return ResponseEntity.ok(toPieceDtos(pieces));
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la récupération des pièces : " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/demandes/{id}/pieces")
    public ResponseEntity<?> updatePieces(@PathVariable("id") Long id,
                                          @RequestBody List<DemandePieceStatusDTO> pieces) {
        try {
            demandeService.updateMultiplePiecesStatus(id, pieces);
            List<DemandePiece> updated = demandeService.getPiecesForDemande(id);
            return ResponseEntity.ok(toPieceDtos(updated));
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la mise à jour des pièces : " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<DemandePieceStatusDTO> toPieceDtos(List<DemandePiece> pieces) {
        return pieces.stream().map(piece -> {
            DemandePieceStatusDTO dto = new DemandePieceStatusDTO();
            dto.setCategoriePieceId(piece.getCategoriePiece().getId());
            dto.setLibelle(piece.getCategoriePiece().getLibelle());
            dto.setIsProvided(piece.getIsProvided());
            return dto;
        }).collect(Collectors.toList());
    }

    private Map<String, Object> toDemandeDto(Demande demande) {
        Map<String, Object> dto = new HashMap<>();
        Demandeur demandeur = demande.getDemandeur();
        EtatCivil etatCivil = demandeur != null ? demandeur.getEtatCivil() : null;

        // Infos Demande
        dto.put("id", demande.getId());
        dto.put("idDemande", demande.getId());
        dto.put("createdAt", demande.getCreatedAt());
        dto.put("updatedAt", demande.getUpdatedAt());

        // Type Demande
        if (demande.getTypeDemande() != null) {
            dto.put("typeDemande", demande.getTypeDemande().getNom());
        }

        // Type Visa (depuis le visa du demandeur)
        if (demandeur != null) {
            var visaOpt = demandeService.getVisaForDemandeur(demandeur.getId());
            if (visaOpt.isPresent()) {
                Visa visa = visaOpt.get();
                if (visa.getTypeVisa() != null) {
                    dto.put("typeVisa", visa.getTypeVisa().getLibelle());
                }
            }
        }

        // Status
        if (demande.getStatus() != null) {
            dto.put("status", demande.getStatus().getStatus());
            dto.put("statusDm", demande.getStatus().getStatus());
        }

        // Infos Demandeur
        if (demandeur != null) {
            dto.put("lastName", demandeur.getNom());
            dto.put("firstNames", demandeur.getPrenom());
            dto.put("birthDate", demandeur.getDateNaissance());

            if (demandeur.getNationalite() != null) {
                dto.put("nationality", demandeur.getNationalite().getNom());
            }

            if (demandeur.getSituationFamille() != null) {
                dto.put("maritalStatus", demandeur.getSituationFamille().getLibelle());
            }
        }

        // Infos EtatCivil
        if (etatCivil != null) {
            dto.put("maidenName", etatCivil.getNomJeuneFille());
            dto.put("birthDate", etatCivil.getDateNaissance());
            dto.put("birthPlace", etatCivil.getLieuNaissance());
            dto.put("homeAddress", etatCivil.getDomicileHabituel());
            dto.put("occupation", etatCivil.getProfession());
            dto.put("employerName", etatCivil.getEmployeur());
            dto.put("employerAddress", etatCivil.getAdresseEmployeur());
        }

        // Infos Passeport (si disponible)
        if (demandeur != null) {
            // Récupérer le premier passeport du demandeur
            var passeportOpt = demandeService.getPasseportForDemandeur(demandeur.getId());
            if (passeportOpt.isPresent()) {
                var passeport = passeportOpt.get();
                dto.put("passeportNumero", passeport.getNumPasseport());
                dto.put("passeportDateDelivrance", passeport.getDateDelivrance());
                dto.put("passeportDateExpiration", passeport.getDateExpiration());
            }

            // Récupérer le visa transformable du demandeur
            var visaTransformableOpt = demandeService.getVisaTransformableForDemandeur(demandeur.getId());
            if (visaTransformableOpt.isPresent()) {
                var visaTransformable = visaTransformableOpt.get();
                dto.put("numeroVisaPrcd", visaTransformable.getNumVisa());
                dto.put("dateDelivranceVisaPrcd", visaTransformable.getDateDelivrance());
                dto.put("dateExpirationVisaPrcd", visaTransformable.getDateExpiration());
            }
        }

        // Pièces
        if (demande.getPieces() != null && !demande.getPieces().isEmpty()) {
            dto.put("pieces", toPieceDtos(new ArrayList<>(demande.getPieces())));
        }

        return dto;
    }
}
