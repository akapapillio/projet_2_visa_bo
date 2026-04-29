package com.project.VISA.dtos;

public class VisaTransformableResponse {

    private Long id;
    private String numeroVisa;
    private Long passeportId;
    private Long demandeurId;

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
