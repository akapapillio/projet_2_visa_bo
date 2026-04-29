package com.project.VISA.dtos;

import java.time.LocalDate;

public class PieceResponse {

    private Long id;
    private String fichierPath;
    private LocalDate dateUpload;
    private Boolean valide;
    private Long demandeurId;
    private Long categoriePieceId;
    private String categoriePiece;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getCategoriePiece() {
        return categoriePiece;
    }

    public void setCategoriePiece(String categoriePiece) {
        this.categoriePiece = categoriePiece;
    }
}
