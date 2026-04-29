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

import com.project.VISA.dtos.PieceRequest;
import com.project.VISA.dtos.PieceResponse;
import com.project.VISA.services.PieceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/pieces")
public class PieceController {

    private final PieceService pieceService;

    public PieceController(PieceService pieceService) {
        this.pieceService = pieceService;
    }

    @GetMapping
    public List<PieceResponse> findAll(@RequestParam(required = false) Long demandeurId) {
        if (demandeurId != null) {
            return pieceService.findByDemandeur(demandeurId);
        }
        return pieceService.findAll();
    }

    @GetMapping("/{id}")
    public PieceResponse findById(@PathVariable Long id) {
        return pieceService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PieceResponse create(@Valid @RequestBody PieceRequest request) {
        return pieceService.create(request);
    }

    @PutMapping("/{id}")
    public PieceResponse update(@PathVariable Long id, @Valid @RequestBody PieceRequest request) {
        return pieceService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        pieceService.delete(id);
    }
}
