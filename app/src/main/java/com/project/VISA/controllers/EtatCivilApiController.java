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
    public ResponseEntity<List<EtatCivil>> getAll() {
        return ResponseEntity.ok(etatCivilService.findAll());
    }

    // ─── READ ONE ─────────────────────────────────────────────────────────────

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return etatCivilService.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("EtatCivil introuvable avec l'id : " + id));
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody EtatCivilDTO dto) {
        try {
            EtatCivil entity = toEntity(dto);
            return etatCivilService.update(id, entity)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("EtatCivil introuvable avec l'id : " + id));
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la mise à jour : " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean deleted = etatCivilService.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("EtatCivil introuvable avec l'id : " + id);
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
