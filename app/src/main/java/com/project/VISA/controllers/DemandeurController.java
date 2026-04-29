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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.VISA.dtos.DemandeurRequest;
import com.project.VISA.dtos.DemandeurResponse;
import com.project.VISA.services.DemandeurService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/demandeurs")
public class DemandeurController {

    private final DemandeurService demandeurService;

    public DemandeurController(DemandeurService demandeurService) {
        this.demandeurService = demandeurService;
    }

    @GetMapping
    public List<DemandeurResponse> findAll() {
        return demandeurService.findAll();
    }

    @GetMapping("/{id}")
    public DemandeurResponse findById(@PathVariable Long id) {
        return demandeurService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DemandeurResponse create(@Valid @RequestBody DemandeurRequest request) {
        return demandeurService.create(request);
    }

    @PutMapping("/{id}")
    public DemandeurResponse update(@PathVariable Long id, @Valid @RequestBody DemandeurRequest request) {
        return demandeurService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        demandeurService.delete(id);
    }
}
