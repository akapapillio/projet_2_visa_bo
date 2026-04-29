package com.project.VISA.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "piece_demande")
public class PieceDemande {

    @EmbeddedId
    private PieceDemandeId id = new PieceDemandeId();

    @ManyToOne
    @MapsId("typeDmId")
    @JoinColumn(name = "id_type_dm")
    private TypeDemande typeDemande;

    @ManyToOne
    @MapsId("categoriePieceId")
    @JoinColumn(name = "id_categorie_piece")
    private CategoriePiece categoriePiece;

    public PieceDemandeId getId() { return id; }
    public void setId(PieceDemandeId id) { this.id = id; }
    public TypeDemande getTypeDemande() { return typeDemande; }
    public void setTypeDemande(TypeDemande typeDemande) { this.typeDemande = typeDemande; }
    public CategoriePiece getCategoriePiece() { return categoriePiece; }
    public void setCategoriePiece(CategoriePiece categoriePiece) { this.categoriePiece = categoriePiece; }

    @Embeddable
    public static class PieceDemandeId implements Serializable {
        private Long typeDmId;
        private Long categoriePieceId;

        public PieceDemandeId() {}
        public PieceDemandeId(Long typeDmId, Long categoriePieceId) {
            this.typeDmId = typeDmId;
            this.categoriePieceId = categoriePieceId;
        }

        public Long getTypeDmId() { return typeDmId; }
        public void setTypeDmId(Long typeDmId) { this.typeDmId = typeDmId; }
        public Long getCategoriePieceId() { return categoriePieceId; }
        public void setCategoriePieceId(Long categoriePieceId) { this.categoriePieceId = categoriePieceId; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PieceDemandeId that = (PieceDemandeId) o;
            return Objects.equals(typeDmId, that.typeDmId) && Objects.equals(categoriePieceId, that.categoriePieceId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(typeDmId, categoriePieceId);
        }
    }
}
