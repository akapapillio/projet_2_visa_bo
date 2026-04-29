package com.project.VISA.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "piece_demande")
public class PieceDemande {

    @EmbeddedId
    private PieceDemandeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("typeDemandeId")
    @JoinColumn(name = "id_type_dm", nullable = false)
    private TypeDemande typeDemande;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("categoriePieceId")
    @JoinColumn(name = "id_categorie_piece", nullable = false)
    private CategoriePiece categoriePiece;

    public PieceDemandeId getId() {
        return id;
    }

    public void setId(PieceDemandeId id) {
        this.id = id;
    }

    public TypeDemande getTypeDemande() {
        return typeDemande;
    }

    public void setTypeDemande(TypeDemande typeDemande) {
        this.typeDemande = typeDemande;
    }

    public CategoriePiece getCategoriePiece() {
        return categoriePiece;
    }

    public void setCategoriePiece(CategoriePiece categoriePiece) {
        this.categoriePiece = categoriePiece;
    }
}
