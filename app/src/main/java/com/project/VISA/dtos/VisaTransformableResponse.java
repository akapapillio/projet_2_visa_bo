package com.project.VISA.dtos;

import java.time.LocalDate;

public class VisaTransformableResponse {

    private Long id;
    private String numeroVisa;
    // private String reference;
    private LocalDate dateDelivrance;
    private LocalDate dateExpiration;

    private Long passeportId;
    private Long demandeurId;

    // ===== GETTERS / SETTERS =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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