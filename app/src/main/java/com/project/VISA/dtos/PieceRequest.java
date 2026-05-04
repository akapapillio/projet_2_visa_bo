package com.project.VISA.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PieceRequest {

    @NotBlank
    private String fichierPath;

    private LocalDate dateUpload;

    private Boolean valide;

    @NotNull
    private Long demandeurId;

    @NotNull
    private Long categoriePieceId;

    public String getFichierPath() {
        return fichierPath;
    }

    public void setFichierPath(String fichierPath) {
        this.fichierPath = fichierPath;
    }

    public LocalDate getDateUpload() {
        return dateUpload;
    }

    public void setDateUpload(LocalDate dateUpload) {
        this.dateUpload = dateUpload;
    }

    public Boolean getValide() {
        return valide;
    }

    public void setValide(Boolean valide) {
        this.valide = valide;
    }

    public Long getDemandeurId() {
        return demandeurId;
    }

    public void setDemandeurId(Long demandeurId) {
        this.demandeurId = demandeurId;
    }

    public Long getCategoriePieceId() {
        return categoriePieceId;
    }

    public void setCategoriePieceId(Long categoriePieceId) {
        this.categoriePieceId = categoriePieceId;
    }
}
