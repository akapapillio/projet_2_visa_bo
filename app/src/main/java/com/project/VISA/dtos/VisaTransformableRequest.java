package com.project.VISA.dtos;

import jakarta.validation.constraints.NotNull;

public class VisaTransformableRequest {

    private String numeroVisa;

    @NotNull
    private Long passeportId;

    @NotNull
    private Long demandeurId;

    public String getNumeroVisa() {
        return numeroVisa;
    }

    public void setNumeroVisa(String numeroVisa) {
        this.numeroVisa = numeroVisa;
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
