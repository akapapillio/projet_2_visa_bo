package com.project.VISA.models;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PieceDemandeId implements Serializable {

    @Column(name = "id_type_dm")
    private Long typeDemandeId;

    @Column(name = "id_categorie_piece")
    private Long categoriePieceId;

    public PieceDemandeId() {
    }

    public PieceDemandeId(Long typeDemandeId, Long categoriePieceId) {
        this.typeDemandeId = typeDemandeId;
        this.categoriePieceId = categoriePieceId;
    }

    public Long getTypeDemandeId() {
        return typeDemandeId;
    }

    public Long getCategoriePieceId() {
        return categoriePieceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PieceDemandeId that)) {
            return false;
        }
        return Objects.equals(typeDemandeId, that.typeDemandeId)
                && Objects.equals(categoriePieceId, that.categoriePieceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeDemandeId, categoriePieceId);
    }
}
