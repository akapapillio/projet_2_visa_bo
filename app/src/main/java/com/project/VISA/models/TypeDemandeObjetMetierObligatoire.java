package com.project.VISA.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "type_demande_objet_metier_obligatoire")
public class TypeDemandeObjetMetierObligatoire {

    @EmbeddedId
    private TypeDemandeObjetMetierObligatoireId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("typeDemandeId")
    @JoinColumn(name = "id_type_dm", nullable = false)
    private TypeDemande typeDemande;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("typeObjetId")
    @JoinColumn(name = "id_type_objet", nullable = false)
    private TypeObjet typeObjet;

    @Column(name = "obligatoire", nullable = false)
    private Boolean obligatoire;

    public TypeDemandeObjetMetierObligatoireId getId() {
        return id;
    }

    public void setId(TypeDemandeObjetMetierObligatoireId id) {
        this.id = id;
    }

    public TypeDemande getTypeDemande() {
        return typeDemande;
    }

    public void setTypeDemande(TypeDemande typeDemande) {
        this.typeDemande = typeDemande;
    }

    public TypeObjet getTypeObjet() {
        return typeObjet;
    }

    public void setTypeObjet(TypeObjet typeObjet) {
        this.typeObjet = typeObjet;
    }

    public Boolean getObligatoire() {
        return obligatoire;
    }

    public void setObligatoire(Boolean obligatoire) {
        this.obligatoire = obligatoire;
    }
}
