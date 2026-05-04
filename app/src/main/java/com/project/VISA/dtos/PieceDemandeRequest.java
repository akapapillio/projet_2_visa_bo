package com.project.VISA.dtos;

import jakarta.validation.constraints.NotNull;

public class PieceDemandeRequest {

    @NotNull
    private Long typeDemandeId;

    @NotNull
    private Long categoriePieceId;

    public Long getTypeDemandeId() {
        return typeDemandeId;
    }

    public void setTypeDemandeId(Long typeDemandeId) {
        this.typeDemandeId = typeDemandeId;
    }

    public Long getCategoriePieceId() {
        return categoriePieceId;
    }

    public void setCategoriePieceId(Long categoriePieceId) {
        this.categoriePieceId = categoriePieceId;
    }
}
