package com.project.VISA.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.VISA.dtos.ReferenceValueResponse;
import com.project.VISA.repositories.CategoriePieceRepository;
import com.project.VISA.repositories.NationaliteRepository;
import com.project.VISA.repositories.SituationFamRepository;
import com.project.VISA.repositories.StatusDmRepository;
import com.project.VISA.repositories.TypeDemandeRepository;
import com.project.VISA.repositories.TypeObjetRepository;
import com.project.VISA.repositories.TypeVisaRepository;

@Service
public class ReferenceService {

    private final TypeDemandeRepository typeDemandeRepository;
    private final StatusDmRepository statusDmRepository;
    private final NationaliteRepository nationaliteRepository;
    private final SituationFamRepository situationFamRepository;
    private final TypeVisaRepository typeVisaRepository;
    private final CategoriePieceRepository categoriePieceRepository;
    private final TypeObjetRepository typeObjetRepository;

    public ReferenceService(
            TypeDemandeRepository typeDemandeRepository,
            StatusDmRepository statusDmRepository,
            NationaliteRepository nationaliteRepository,
            SituationFamRepository situationFamRepository,
            TypeVisaRepository typeVisaRepository,
            CategoriePieceRepository categoriePieceRepository,
            TypeObjetRepository typeObjetRepository) {
        this.typeDemandeRepository = typeDemandeRepository;
        this.statusDmRepository = statusDmRepository;
        this.nationaliteRepository = nationaliteRepository;
        this.situationFamRepository = situationFamRepository;
        this.typeVisaRepository = typeVisaRepository;
        this.categoriePieceRepository = categoriePieceRepository;
        this.typeObjetRepository = typeObjetRepository;
    }

    public List<ReferenceValueResponse> getTypeDemandes() {
        return typeDemandeRepository.findAll().stream()
                .map(it -> new ReferenceValueResponse(it.getId(), it.getNom()))
                .toList();
    }

    public List<ReferenceValueResponse> getStatusDemandes() {
        return statusDmRepository.findAll().stream()
                .map(it -> new ReferenceValueResponse(it.getId(), it.getCode()))
                .toList();
    }

    public List<ReferenceValueResponse> getNationalites() {
        return nationaliteRepository.findAll().stream()
                .map(it -> new ReferenceValueResponse(it.getId(), it.getLibelle()))
                .toList();
    }

    public List<ReferenceValueResponse> getSituationsFamiliales() {
        return situationFamRepository.findAll().stream()
                .map(it -> new ReferenceValueResponse(it.getId(), it.getLibelle()))
                .toList();
    }

    public List<ReferenceValueResponse> getTypesVisa() {
        return typeVisaRepository.findAll().stream()
                .map(it -> new ReferenceValueResponse(it.getId(), it.getLibelle()))
                .toList();
    }

    public List<ReferenceValueResponse> getCategoriesPieces() {
        return categoriePieceRepository.findAll().stream()
                .map(it -> new ReferenceValueResponse(it.getId(), it.getLibelle()))
                .toList();
    }

    public List<ReferenceValueResponse> getTypesObjets() {
        return typeObjetRepository.findAll().stream()
                .map(it -> new ReferenceValueResponse(it.getId(), it.getNom()))
                .toList();
    }
}
