package com.project.VISA.dtos;

import jakarta.validation.constraints.NotNull;

public class CarteResidentRequest {

    private Integer numero;

    @NotNull
    private Long demandeurId;

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Long getDemandeurId() {
        return demandeurId;
    }

    public void setDemandeurId(Long demandeurId) {
        this.demandeurId = demandeurId;
    }
}
