package com.project.VISA.dtos;

public class DemandePieceStatusDTO {
    private Long categoriePieceId;
    private String libelle;
    private Boolean isProvided;

    public Long getCategoriePieceId() { return categoriePieceId; }
    public void setCategoriePieceId(Long categoriePieceId) { this.categoriePieceId = categoriePieceId; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public Boolean getIsProvided() { return isProvided; }
    public void setIsProvided(Boolean isProvided) { this.isProvided = isProvided; }
}
