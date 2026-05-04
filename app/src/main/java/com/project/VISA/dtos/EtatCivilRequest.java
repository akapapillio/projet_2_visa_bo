package com.project.VISA.dtos;

import jakarta.validation.constraints.NotNull;

public class EtatCivilRequest {

    @NotNull
    private Long demandeurId;

    public Long getDemandeurId() {
        return demandeurId;
    }

    public void setDemandeurId(Long demandeurId) {
        this.demandeurId = demandeurId;
    }
}
