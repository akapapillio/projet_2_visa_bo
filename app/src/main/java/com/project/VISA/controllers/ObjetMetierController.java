package com.project.VISA.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.VISA.dtos.CarteResidentRequest;
import com.project.VISA.dtos.CarteResidentResponse;
import com.project.VISA.dtos.EtatCivilRequest;
import com.project.VISA.dtos.EtatCivilResponse;
import com.project.VISA.dtos.PasseportRequest;
import com.project.VISA.dtos.PasseportResponse;
import com.project.VISA.dtos.VisaRequest;
import com.project.VISA.dtos.VisaResponse;
import com.project.VISA.dtos.VisaTransformableRequest;
import com.project.VISA.dtos.VisaTransformableResponse;
import com.project.VISA.services.ObjetMetierService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/objets")
public class ObjetMetierController {

    private final ObjetMetierService objetMetierService;

    public ObjetMetierController(ObjetMetierService objetMetierService) {
        this.objetMetierService = objetMetierService;
    }

    @PostMapping("/passeports")
    @ResponseStatus(HttpStatus.CREATED)
    public PasseportResponse createPasseport(@Valid @RequestBody PasseportRequest request) {
        return objetMetierService.createPasseport(request);
    }

    @GetMapping("/passeports")
    public List<PasseportResponse> getPasseports(@RequestParam(required = false) Long demandeurId) {
        return objetMetierService.getPasseports(demandeurId);
    }

    @PostMapping("/visas")
    @ResponseStatus(HttpStatus.CREATED)
    public VisaResponse createVisa(@Valid @RequestBody VisaRequest request) {
        return objetMetierService.createVisa(request);
    }

    @GetMapping("/visas")
    public List<VisaResponse> getVisas(@RequestParam(required = false) Long demandeurId) {
        return objetMetierService.getVisas(demandeurId);
    }

    @PostMapping("/visas-transformables")
    @ResponseStatus(HttpStatus.CREATED)
    public VisaTransformableResponse createVisaTransformable(@Valid @RequestBody VisaTransformableRequest request) {
        return objetMetierService.createVisaTransformable(request);
    }

    @GetMapping("/visas-transformables")
    public List<VisaTransformableResponse> getVisaTransformables(@RequestParam(required = false) Long demandeurId) {
        return objetMetierService.getVisaTransformables(demandeurId);
    }

    @PostMapping("/etats-civils")
    @ResponseStatus(HttpStatus.CREATED)
    public EtatCivilResponse createEtatCivil(@Valid @RequestBody EtatCivilRequest request) {
        return objetMetierService.createEtatCivil(request);
    }

    @GetMapping("/etats-civils")
    public List<EtatCivilResponse> getEtatsCivil(@RequestParam(required = false) Long demandeurId) {
        return objetMetierService.getEtatsCivil(demandeurId);
    }

    @PostMapping("/cartes-resident")
    @ResponseStatus(HttpStatus.CREATED)
    public CarteResidentResponse createCarteResident(@Valid @RequestBody CarteResidentRequest request) {
        return objetMetierService.createCarteResident(request);
    }

    @GetMapping("/cartes-resident")
    public List<CarteResidentResponse> getCartesResident(@RequestParam(required = false) Long demandeurId) {
        return objetMetierService.getCartesResident(demandeurId);
    }
}
