package com.project.VISA.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.VISA.dtos.DemandeRequest;
import com.project.VISA.dtos.DemandeResponse;
import com.project.VISA.dtos.DemandeValidationResponse;
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
}
