package com.project.VISA.controllers;

import com.project.VISA.dtos.DemandeDTO;
import com.project.VISA.models.Demande;
import com.project.VISA.services.DemandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            return ResponseEntity.ok(demandes);
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
                return ResponseEntity.ok(result.get());
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
                return ResponseEntity.ok("Demande supprimée avec succès");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Demande non trouvée avec l'id : " + id);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la suppression de la demande : " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
