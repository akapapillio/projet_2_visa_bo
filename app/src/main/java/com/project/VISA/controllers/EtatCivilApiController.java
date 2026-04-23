package com.project.VISA.controllers;

import com.project.VISA.dtos.EtatCivilDTO;
import com.project.VISA.models.EtatCivil;
import com.project.VISA.services.EtatCivilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * API REST — EtatCivil
 * Base URL : /api/visa/etat-civil
 *
 * POST   /api/visa/etat-civil         → Créer un état civil
 * GET    /api/visa/etat-civil         → Lister tous les états civils
 * GET    /api/visa/etat-civil/{id}    → Obtenir un état civil par ID
 * PUT    /api/visa/etat-civil/{id}    → Modifier un état civil
 * DELETE /api/visa/etat-civil/{id}    → Supprimer un état civil
 */
@RestController
@RequestMapping("/api/visa/etat-civil")
@CrossOrigin(origins = "*")
public class EtatCivilApiController {

    @Autowired
    private EtatCivilService etatCivilService;

    // ─── CREATE ───────────────────────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<?> create(@RequestBody EtatCivilDTO dto) {
        try {
            EtatCivil entity = toEntity(dto);
            EtatCivil saved = etatCivilService.save(entity);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la création : " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ─── READ ALL ─────────────────────────────────────────────────────────────

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body("Consultation restreinte (Sprint 2).");
    }

    // ─── READ ONE ─────────────────────────────────────────────────────────────

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body("Consultation de l'ID " + id + " restreinte (Sprint 2).");
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody EtatCivilDTO dto) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body("Modification restreinte (Sprint 2).");
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body("Suppression restreinte (Sprint 2).");
    }

    // ─── Mapper DTO → Entity ──────────────────────────────────────────────────

    private EtatCivil toEntity(EtatCivilDTO dto) {
        EtatCivil entity = new EtatCivil();
        entity.setNom(dto.getNom());
        entity.setPrenoms(dto.getPrenoms());
        entity.setNomJeuneFille(dto.getNomJeuneFille());
        entity.setDateNaissance(dto.getDateNaissance());
        entity.setSituationFamille(dto.getSituationFamille());
        entity.setNationalite(dto.getNationalite());
        entity.setDomicileHabituel(dto.getDomicileHabituel());
        entity.setProfession(dto.getProfession());
        entity.setEmployeur(dto.getEmployeur());
        entity.setAdresseEmployeur(dto.getAdresseEmployeur());
        return entity;
    }
}
