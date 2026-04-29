package com.project.VISA.models;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class TypeDemandeObjetMetierObligatoireId implements Serializable {

    @Column(name = "id_type_dm")
    private Long typeDemandeId;

    @Column(name = "id_type_objet")
    private Long typeObjetId;

    public TypeDemandeObjetMetierObligatoireId() {
    }

    public TypeDemandeObjetMetierObligatoireId(Long typeDemandeId, Long typeObjetId) {
        this.typeDemandeId = typeDemandeId;
        this.typeObjetId = typeObjetId;
    }

    public Long getTypeDemandeId() {
        return typeDemandeId;
    }

    public Long getTypeObjetId() {
        return typeObjetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeDemandeObjetMetierObligatoireId that)) {
            return false;
        }
        return Objects.equals(typeDemandeId, that.typeDemandeId)
                && Objects.equals(typeObjetId, that.typeObjetId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeDemandeId, typeObjetId);
    }
}
