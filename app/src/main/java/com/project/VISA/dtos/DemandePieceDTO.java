package com.project.VISA.dtos;

public class DemandePieceDTO {
    private Long categoriePieceId;
    private String categoriePieceLibelle;
    private Boolean isProvided;

    public DemandePieceDTO() {}

    public DemandePieceDTO(Long categoriePieceId, String categoriePieceLibelle, Boolean isProvided) {
        this.categoriePieceId = categoriePieceId;
        this.categoriePieceLibelle = categoriePieceLibelle;
        this.isProvided = isProvided;
    }

    public Long getCategoriePieceId() { return categoriePieceId; }
    public void setCategoriePieceId(Long categoriePieceId) { this.categoriePieceId = categoriePieceId; }

    public String getCategoriePieceLibelle() { return categoriePieceLibelle; }
    public void setCategoriePieceLibelle(String categoriePieceLibelle) { this.categoriePieceLibelle = categoriePieceLibelle; }

    public Boolean getIsProvided() { return isProvided; }
    public void setIsProvided(Boolean isProvided) { this.isProvided = isProvided; }
}
