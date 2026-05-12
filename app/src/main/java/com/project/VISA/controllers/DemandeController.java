package com.project.VISA.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.project.VISA.dtos.DemandeRequest;
import com.project.VISA.dtos.DemandeResponse;
import com.project.VISA.dtos.DemandeValidationResponse;
import com.project.VISA.dtos.FileUploadResponse;
import com.project.VISA.dtos.StatusUpdateRequest;
import com.project.VISA.services.DemandeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/demandes")
public class DemandeController {

    private final DemandeService demandeService;

    public DemandeController(DemandeService demandeService) {
        this.demandeService = demandeService;
    }

    @GetMapping
    public List<DemandeResponse> findAll(@RequestParam(required = false) Long demandeurId) {
        if (demandeurId != null) {
            return demandeService.findByDemandeur(demandeurId);
        }
        return demandeService.findAll();
    }

    @GetMapping("/{id}")
    public DemandeResponse findById(@PathVariable Long id) {
        return demandeService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DemandeResponse create(@Valid @RequestBody DemandeRequest request) {
        return demandeService.create(request);
    }

    @PutMapping("/{id}")
    public DemandeResponse update(@PathVariable Long id, @Valid @RequestBody DemandeRequest request) {
        return demandeService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        demandeService.delete(id);
    }

    @GetMapping("/{id}/validation")
    public DemandeValidationResponse validate(@PathVariable Long id) {
        return demandeService.validate(id);
    }

    // ==================== Photo & Signature Endpoints ====================

    /**
     * POST /api/v1/demandes/{id}/photo
     * Accepte un fichier image multipart, valide format et taille (max 5MB),
     * sauvegarde le fichier et retourne l'URL.
     */
    @PostMapping(value = "/{id}/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileUploadResponse uploadPhoto(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        return demandeService.uploadPhoto(id, file);
    }

    /**
     * POST /api/v1/demandes/{id}/signature
     * Accepte un fichier image multipart, valide format et taille,
     * sauvegarde le fichier et retourne l'URL.
     */
    @PostMapping(value = "/{id}/signature", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileUploadResponse uploadSignature(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        return demandeService.uploadSignature(id, file);
    }

    // ==================== Status Transition Endpoint ====================

    /**
     * PATCH /api/v1/demandes/{id}/status
     * Permet la transition d'état. Valide que photo et signature sont présentes
     * pour PHOTO_SIGNATURE_COMPLETE. Refuse automatiquement si manquantes.
     */
    @PatchMapping("/{id}/status")
    public DemandeResponse updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusUpdateRequest request) {
        return demandeService.updateStatus(id, request.getStatusCode());
    }
}
