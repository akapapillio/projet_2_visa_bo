package com.project.VISA.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.VISA.dtos.PieceDemandeRequest;
import com.project.VISA.services.RuleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/regles")
public class RegleController {

    private final RuleService ruleService;

    public RegleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @GetMapping("/types-demandes/{typeDemandeId}/pieces-obligatoires")
    public List<String> getPiecesObligatoires(@PathVariable Long typeDemandeId) {
        return ruleService.getPieceRules(typeDemandeId)
                .stream()
                .map(rule -> rule.getCategoriePiece().getLibelle())
                .toList();
    }

    @PostMapping("/pieces-obligatoires")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPieceRule(@Valid @RequestBody PieceDemandeRequest request) {
        ruleService.createPieceRule(request);
    }

    @DeleteMapping("/types-demandes/{typeDemandeId}/pieces-obligatoires/{categoriePieceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePieceRule(@PathVariable Long typeDemandeId, @PathVariable Long categoriePieceId) {
        ruleService.deletePieceRule(typeDemandeId, categoriePieceId);
    }

    @GetMapping("/types-demandes/{typeDemandeId}/objets-obligatoires")
    public List<String> getObjetsObligatoires(@PathVariable Long typeDemandeId) {
        return ruleService.getRequiredObjectNames(typeDemandeId);
    }
}
