package com.project.VISA.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.VISA.dtos.ReferenceValueResponse;
import com.project.VISA.services.ReferenceService;

@RestController
@RequestMapping("/api/v1/references")
public class ReferenceController {

    private final ReferenceService referenceService;

    public ReferenceController(ReferenceService referenceService) {
        this.referenceService = referenceService;
    }

    @GetMapping("/types-demandes")
    public List<ReferenceValueResponse> typeDemandes() {
        return referenceService.getTypeDemandes();
    }

    @GetMapping("/status-demandes")
    public List<ReferenceValueResponse> statusDemandes() {
        return referenceService.getStatusDemandes();
    }

    @GetMapping("/nationalites")
    public List<ReferenceValueResponse> nationalites() {
        return referenceService.getNationalites();
    }

    @GetMapping("/situations-familiales")
    public List<ReferenceValueResponse> situationsFamiliales() {
        return referenceService.getSituationsFamiliales();
    }

    @GetMapping("/types-visa")
    public List<ReferenceValueResponse> typesVisa() {
        return referenceService.getTypesVisa();
    }

    @GetMapping("/categories-pieces")
    public List<ReferenceValueResponse> categoriesPieces() {
        return referenceService.getCategoriesPieces();
    }

    @GetMapping("/types-objets")
    public List<ReferenceValueResponse> typesObjets() {
        return referenceService.getTypesObjets();
    }
}
