package com.project.VISA.controllers;

import com.project.VISA.dtos.VisaTransformableDTO;
import com.project.VISA.models.VisaTransformable;
import com.project.VISA.services.VisaTransformableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * API REST — VisaTransformable
 * Base URL : /api/visa/visa-transformable
 *
 * POST   /api/visa/visa-transformable         → Enregistrer un visa précédent
 * GET    /api/visa/visa-transformable         → Lister tous les visas précédents
 * GET    /api/visa/visa-transformable/{id}    → Obtenir un visa précédent par ID
 * PUT    /api/visa/visa-transformable/{id}    → Modifier un visa précédent
 * DELETE /api/visa/visa-transformable/{id}    → Supprimer un visa précédent
 */
@RestController
@RequestMapping("/api/visa/visa-transformable")
@CrossOrigin(origins = "*")
public class VisaTransformableApiController {

    @Autowired
    private VisaTransformableService visaTransformableService;

    // ─── CREATE ───────────────────────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<?> create(@RequestBody VisaTransformableDTO dto) {
        try {
            VisaTransformable entity = toEntity(dto);
            VisaTransformable saved = visaTransformableService.save(entity);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la création : " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ─── READ ALL ─────────────────────────────────────────────────────────────

    @GetMapping
    public ResponseEntity<List<VisaTransformable>> getAll() {
        return ResponseEntity.ok(visaTransformableService.findAll());
    }

    // ─── READ ONE ─────────────────────────────────────────────────────────────

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return visaTransformableService.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("VisaTransformable introuvable avec l'id : " + id));
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody VisaTransformableDTO dto) {
        try {
            VisaTransformable entity = toEntity(dto);
            return visaTransformableService.update(id, entity)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("VisaTransformable introuvable avec l'id : " + id));
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la mise à jour : " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean deleted = visaTransformableService.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("VisaTransformable introuvable avec l'id : " + id);
    }

    // ─── Mapper DTO → Entity ──────────────────────────────────────────────────

    private VisaTransformable toEntity(VisaTransformableDTO dto) {
        VisaTransformable entity = new VisaTransformable();
        entity.setNumeroVisa(dto.getNumeroVisa());
        entity.setDateDelivrance(dto.getDateDelivrance());
        entity.setDateExpiration(dto.getDateExpiration());
        return entity;
    }
}
