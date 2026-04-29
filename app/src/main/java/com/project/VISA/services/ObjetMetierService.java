package com.project.VISA.services;

import java.util.List;

import org.springframework.stereotype.Service;

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
import com.project.VISA.models.CarteResident;
import com.project.VISA.models.EtatCivil;
import com.project.VISA.models.Passeport;
import com.project.VISA.models.Visa;
import com.project.VISA.models.VisaTransformable;
import com.project.VISA.repositories.CarteResidentRepository;
import com.project.VISA.repositories.EtatCivilRepository;
import com.project.VISA.repositories.PasseportRepository;
import com.project.VISA.repositories.TypeVisaRepository;
import com.project.VISA.repositories.VisaRepository;
import com.project.VISA.repositories.VisaTransformableRepository;

@Service
public class ObjetMetierService {

    private final DemandeurService demandeurService;
    private final PasseportRepository passeportRepository;
    private final VisaRepository visaRepository;
    private final TypeVisaRepository typeVisaRepository;
    private final VisaTransformableRepository visaTransformableRepository;
    private final EtatCivilRepository etatCivilRepository;
    private final CarteResidentRepository carteResidentRepository;

    public ObjetMetierService(
            DemandeurService demandeurService,
            PasseportRepository passeportRepository,
            VisaRepository visaRepository,
            TypeVisaRepository typeVisaRepository,
            VisaTransformableRepository visaTransformableRepository,
            EtatCivilRepository etatCivilRepository,
            CarteResidentRepository carteResidentRepository) {
        this.demandeurService = demandeurService;
        this.passeportRepository = passeportRepository;
        this.visaRepository = visaRepository;
        this.typeVisaRepository = typeVisaRepository;
        this.visaTransformableRepository = visaTransformableRepository;
        this.etatCivilRepository = etatCivilRepository;
        this.carteResidentRepository = carteResidentRepository;
    }

    public PasseportResponse createPasseport(PasseportRequest request) {
        Passeport passeport = new Passeport();
        passeport.setNumeroPasseport(request.getNumeroPasseport());
        passeport.setDateExpiration(request.getDateExpiration());
        passeport.setDateDelivrance(request.getDateDelivrance());
        passeport.setDemandeur(demandeurService.findEntity(request.getDemandeurId()));
        return toResponse(passeportRepository.save(passeport));
    }

    public List<PasseportResponse> getPasseports(Long demandeurId) {
        if (demandeurId != null) {
            return passeportRepository.findByDemandeurId(demandeurId).stream().map(this::toResponse).toList();
        }
        return passeportRepository.findAll().stream().map(this::toResponse).toList();
    }

    public VisaResponse createVisa(VisaRequest request) {
        Visa visa = new Visa();
        visa.setDateExpiration(request.getDateExpiration());
        visa.setNom(request.getNom());
        visa.setPrenom(request.getPrenom());
        visa.setReference(request.getReference());
        visa.setNumeroVisa(request.getNumeroVisa());
        visa.setDateDelivrance(request.getDateDelivrance());
        visa.setDateModification(request.getDateModification());
        visa.setDemandeur(demandeurService.findEntity(request.getDemandeurId()));
        visa.setTypeVisa(typeVisaRepository.findById(request.getTypeVisaId())
                .orElseThrow(() -> new ResourceNotFoundException("Type visa introuvable: " + request.getTypeVisaId())));
        return toResponse(visaRepository.save(visa));
    }

    public List<VisaResponse> getVisas(Long demandeurId) {
        if (demandeurId != null) {
            return visaRepository.findByDemandeurId(demandeurId).stream().map(this::toResponse).toList();
        }
        return visaRepository.findAll().stream().map(this::toResponse).toList();
    }

    public VisaTransformableResponse createVisaTransformable(VisaTransformableRequest request) {
        VisaTransformable visaTransformable = new VisaTransformable();
        visaTransformable.setNumeroVisa(request.getNumeroVisa());
        visaTransformable.setDemandeur(demandeurService.findEntity(request.getDemandeurId()));
        visaTransformable.setPasseport(passeportRepository.findById(request.getPasseportId())
                .orElseThrow(() -> new ResourceNotFoundException("Passeport introuvable: " + request.getPasseportId())));
        return toResponse(visaTransformableRepository.save(visaTransformable));
    }

    public List<VisaTransformableResponse> getVisaTransformables(Long demandeurId) {
        if (demandeurId != null) {
            return visaTransformableRepository.findByDemandeurId(demandeurId).stream().map(this::toResponse).toList();
        }
        return visaTransformableRepository.findAll().stream().map(this::toResponse).toList();
    }

    public EtatCivilResponse createEtatCivil(EtatCivilRequest request) {
        EtatCivil etatCivil = new EtatCivil();
        etatCivil.setDemandeur(demandeurService.findEntity(request.getDemandeurId()));
        return toResponse(etatCivilRepository.save(etatCivil));
    }

    public List<EtatCivilResponse> getEtatsCivil(Long demandeurId) {
        if (demandeurId != null) {
            return etatCivilRepository.findByDemandeurId(demandeurId).stream().map(this::toResponse).toList();
        }
        return etatCivilRepository.findAll().stream().map(this::toResponse).toList();
    }

    public CarteResidentResponse createCarteResident(CarteResidentRequest request) {
        CarteResident carteResident = new CarteResident();
        carteResident.setNumero(request.getNumero());
        carteResident.setDemandeur(demandeurService.findEntity(request.getDemandeurId()));
        return toResponse(carteResidentRepository.save(carteResident));
    }

    public List<CarteResidentResponse> getCartesResident(Long demandeurId) {
        if (demandeurId != null) {
            return carteResidentRepository.findByDemandeurId(demandeurId).stream().map(this::toResponse).toList();
        }
        return carteResidentRepository.findAll().stream().map(this::toResponse).toList();
    }

    private PasseportResponse toResponse(Passeport entity) {
        PasseportResponse response = new PasseportResponse();
        response.setId(entity.getId());
        response.setNumeroPasseport(entity.getNumeroPasseport());
        response.setDateExpiration(entity.getDateExpiration());
        response.setDateDelivrance(entity.getDateDelivrance());
        response.setDemandeurId(entity.getDemandeur().getId());
        return response;
    }

    private VisaResponse toResponse(Visa entity) {
        VisaResponse response = new VisaResponse();
        response.setId(entity.getId());
        response.setDateExpiration(entity.getDateExpiration());
        response.setNom(entity.getNom());
        response.setPrenom(entity.getPrenom());
        response.setReference(entity.getReference());
        response.setNumeroVisa(entity.getNumeroVisa());
        response.setDateDelivrance(entity.getDateDelivrance());
        response.setDateModification(entity.getDateModification());
        response.setTypeVisaId(entity.getTypeVisa().getId());
        response.setTypeVisa(entity.getTypeVisa().getLibelle());
        response.setDemandeurId(entity.getDemandeur().getId());
        return response;
    }

    private VisaTransformableResponse toResponse(VisaTransformable entity) {
        VisaTransformableResponse response = new VisaTransformableResponse();
        response.setId(entity.getId());
        response.setNumeroVisa(entity.getNumeroVisa());
        response.setPasseportId(entity.getPasseport().getId());
        response.setDemandeurId(entity.getDemandeur().getId());
        return response;
    }

    private EtatCivilResponse toResponse(EtatCivil entity) {
        EtatCivilResponse response = new EtatCivilResponse();
        response.setId(entity.getId());
        response.setDemandeurId(entity.getDemandeur().getId());
        return response;
    }

    private CarteResidentResponse toResponse(CarteResident entity) {
        CarteResidentResponse response = new CarteResidentResponse();
        response.setId(entity.getId());
        response.setNumero(entity.getNumero());
        response.setDemandeurId(entity.getDemandeur().getId());
        return response;
    }
}
