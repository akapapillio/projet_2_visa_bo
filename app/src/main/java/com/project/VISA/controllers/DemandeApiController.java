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
    public ResponseEntity<List<Demande>> getAllDemandes() {
        List<Demande> demandes = demandeService.findAll();
        return ResponseEntity.ok(demandes);
    }

    // ─── READ ONE ─────────────────────────────────────────────────────────────

    @GetMapping("/demandes/{id}")
    public ResponseEntity<?> getDemandeById(@PathVariable Long id) {
        return demandeService.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Demande introuvable avec l'id : " + id));
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────

    @PutMapping("/demandes/{id}")
    public ResponseEntity<?> updateDemande(@PathVariable Long id,
                                           @RequestBody DemandeDTO demandeDTO) {
        try {
            return demandeService.update(id, demandeDTO)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Demande introuvable avec l'id : " + id));
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la mise à jour : " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────

    @DeleteMapping("/demandes/{id}")
    public ResponseEntity<?> deleteDemande(@PathVariable Long id) {
        boolean deleted = demandeService.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Demande introuvable avec l'id : " + id);
    }
}
