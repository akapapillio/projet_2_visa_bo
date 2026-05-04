package com.project.VISA.controllers;

import com.project.VISA.models.CategoriePiece;
import com.project.VISA.models.Nationalite;
import com.project.VISA.models.SituationFam;
import com.project.VISA.models.TypeDemande;
import com.project.VISA.models.TypeVisa;
import com.project.VISA.repositories.CategoriePieceRepository;
import com.project.VISA.repositories.NationaliteRepository;
import com.project.VISA.repositories.SituationFamRepository;
import com.project.VISA.repositories.TypeDemandeRepository;
import com.project.VISA.repositories.TypeVisaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/visa")
@CrossOrigin(origins = "*")
public class ReferenceApiController {

    @Autowired private TypeDemandeRepository typeDemandeRepository;
    @Autowired private TypeVisaRepository typeVisaRepository;
    @Autowired private NationaliteRepository nationaliteRepository;
    @Autowired private SituationFamRepository situationFamRepository;
    @Autowired private CategoriePieceRepository categoriePieceRepository;

    @GetMapping("/type-demandes")
    public List<TypeDemande> getTypeDemandes() {
        return typeDemandeRepository.findAll();
    }

    @GetMapping("/type-visas")
    public List<TypeVisa> getTypeVisas() {
        return typeVisaRepository.findAll();
    }

    @GetMapping("/nationalites")
    public List<Nationalite> getNationalites() {
        return nationaliteRepository.findAll();
    }

    @GetMapping("/situations-familiales")
    public List<SituationFam> getSituationsFamiliales() {
        return situationFamRepository.findAll();
    }

    @GetMapping("/categories-pieces")
    public List<CategoriePiece> getCategoriesPieces() {
        return categoriePieceRepository.findAll();
    }
}
