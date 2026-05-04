package com.project.VISA.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.VISA.dtos.DemandeurRequest;
import com.project.VISA.dtos.DemandeurResponse;
import com.project.VISA.models.Demandeur;
import com.project.VISA.repositories.DemandeurRepository;
import com.project.VISA.repositories.NationaliteRepository;
import com.project.VISA.repositories.SituationFamRepository;

@Service
public class DemandeurService {

    private final DemandeurRepository demandeurRepository;
    private final SituationFamRepository situationFamRepository;
    private final NationaliteRepository nationaliteRepository;

    public DemandeurService(
            DemandeurRepository demandeurRepository,
            SituationFamRepository situationFamRepository,
            NationaliteRepository nationaliteRepository) {
        this.demandeurRepository = demandeurRepository;
        this.situationFamRepository = situationFamRepository;
        this.nationaliteRepository = nationaliteRepository;
    }

    public List<DemandeurResponse> findAll() {
        return demandeurRepository.findAll().stream().map(this::toResponse).toList();
    }

    public DemandeurResponse findById(Long id) {
        return toResponse(findEntity(id));
    }

    public DemandeurResponse create(DemandeurRequest request) {
        Demandeur demandeur = new Demandeur();
        applyRequest(request, demandeur);
        return toResponse(demandeurRepository.save(demandeur));
    }

    public DemandeurResponse update(Long id, DemandeurRequest request) {
        Demandeur demandeur = findEntity(id);
        applyRequest(request, demandeur);
        return toResponse(demandeurRepository.save(demandeur));
    }

    public void delete(Long id) {
        Demandeur demandeur = findEntity(id);
        demandeurRepository.delete(demandeur);
    }

    public Demandeur findEntity(Long id) {
        return demandeurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Demandeur introuvable: " + id));
    }

    private void applyRequest(DemandeurRequest request, Demandeur demandeur) {
        demandeur.setNom(request.getNom());
        demandeur.setPrenom(request.getPrenom());
        demandeur.setDateNaissance(request.getDateNaissance());
        demandeur.setLieuNaissance(request.getLieuNaissance());

        var situation = situationFamRepository.findById(request.getSituationFamilialeId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Situation familiale introuvable: " + request.getSituationFamilialeId()));
        var nationalite = nationaliteRepository.findById(request.getNationaliteId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Nationalite introuvable: " + request.getNationaliteId()));

        demandeur.setSituationFamiliale(situation);
        demandeur.setNationalite(nationalite);
    }

    private DemandeurResponse toResponse(Demandeur entity) {
        DemandeurResponse response = new DemandeurResponse();
        response.setId(entity.getId());
        response.setNom(entity.getNom());
        response.setPrenom(entity.getPrenom());
        response.setDateNaissance(entity.getDateNaissance());
        response.setLieuNaissance(entity.getLieuNaissance());
        response.setSituationFamilialeId(entity.getSituationFamiliale().getId());
        response.setSituationFamiliale(entity.getSituationFamiliale().getLibelle());
        response.setNationaliteId(entity.getNationalite().getId());
        response.setNationalite(entity.getNationalite().getLibelle());
        return response;
    }
}
