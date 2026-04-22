package com.project.VISA.controllers;

import com.project.VISA.models.Demandeur;
import com.project.VISA.services.DemandeurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visa/demandeurs")
@CrossOrigin(origins = "*")
public class DemandeurApiController {

    @Autowired
    private DemandeurService demandeurService;

    @GetMapping
    public List<Demandeur> getAll() {
        return demandeurService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Demandeur> getById(@PathVariable Long id) {
        return demandeurService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Demandeur> create(@RequestBody Demandeur demandeur) {
        return new ResponseEntity<>(demandeurService.save(demandeur), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (demandeurService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
