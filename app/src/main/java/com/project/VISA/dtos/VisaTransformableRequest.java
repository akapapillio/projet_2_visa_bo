package com.project.VISA.dtos;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class VisaTransformableRequest {

    private String numeroVisa;

    // private String reference;

    private LocalDate dateDelivrance;

    private LocalDate dateExpiration;

    @NotNull
    private Long passeportId;

    @NotNull
    private Long demandeurId;

    // ===== GETTERS / SETTERS =====

    public String getNumeroVisa() {
        return numeroVisa;
    }

    public void setNumeroVisa(String numeroVisa) {
        this.numeroVisa = numeroVisa;
    }

    // public String getReference() {
    //     return reference;
    // }

    // public void setReference(String reference) {
    //     this.reference = reference;
    // }

    public LocalDate getDateDelivrance() {
        return dateDelivrance;
    }

    public void setDateDelivrance(LocalDate dateDelivrance) {
        this.dateDelivrance = dateDelivrance;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public Long getPasseportId() {
        return passeportId;
    }

    public void setPasseportId(Long passeportId) {
        this.passeportId = passeportId;
    }

    public Long getDemandeurId() {
        return demandeurId;
    }

    public void setDemandeurId(Long demandeurId) {
        this.demandeurId = demandeurId;
    }
}