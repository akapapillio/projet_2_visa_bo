package com.project.VISA.controllers;

import com.project.VISA.dtos.DemandeDTO;
import com.project.VISA.models.Demande;
import com.project.VISA.services.DemandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/visa")
@CrossOrigin(origins = "*") // Autoriser le FO à faire des requêtes
public class DemandeApiController {

    @Autowired
    private DemandeService demandeService;

    @PostMapping("/demande_transformation")
    public ResponseEntity<?> creerDemandeTransformation(@RequestBody DemandeDTO demandeDTO) {
        try {
            Demande demande = demandeService.creerNouvelleDemande(demandeDTO);
            return new ResponseEntity<>(demande, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la création de la demande: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
