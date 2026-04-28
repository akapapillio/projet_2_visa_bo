package com.project.VISA.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "demande_piece")
public class DemandePiece {

    @EmbeddedId
    private DemandePieceId id = new DemandePieceId();

    @ManyToOne
    @MapsId("demandeId")
    @JoinColumn(name = "id_demande")
    @JsonIgnore
    private Demande demande;

    @ManyToOne
    @MapsId("categoriePieceId")
    @JoinColumn(name = "id_categorie_piece")
    private CategoriePiece categoriePiece;

    @Column(name = "is_provided", nullable = false)
    private Boolean isProvided = false;

    public DemandePiece() {}

    public DemandePiece(Demande demande, CategoriePiece categoriePiece) {
        this.demande = demande;
        this.categoriePiece = categoriePiece;
        this.isProvided = false;
        this.id = new DemandePieceId(demande.getId(), categoriePiece.getId());
    }

    public DemandePieceId getId() { return id; }
    public void setId(DemandePieceId id) { this.id = id; }

    public Demande getDemande() { return demande; }
    public void setDemande(Demande demande) { this.demande = demande; }

    public CategoriePiece getCategoriePiece() { return categoriePiece; }
    public void setCategoriePiece(CategoriePiece categoriePiece) { this.categoriePiece = categoriePiece; }

    public Boolean getIsProvided() { return isProvided; }
    public void setIsProvided(Boolean isProvided) { this.isProvided = isProvided; }

    @Embeddable
    public static class DemandePieceId implements Serializable {
        private Long demandeId;
        private Long categoriePieceId;

        public DemandePieceId() {}

        public DemandePieceId(Long demandeId, Long categoriePieceId) {
            this.demandeId = demandeId;
            this.categoriePieceId = categoriePieceId;
        }

        public Long getDemandeId() { return demandeId; }
        public void setDemandeId(Long demandeId) { this.demandeId = demandeId; }

        public Long getCategoriePieceId() { return categoriePieceId; }
        public void setCategoriePieceId(Long categoriePieceId) { this.categoriePieceId = categoriePieceId; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DemandePieceId that = (DemandePieceId) o;
            return Objects.equals(demandeId, that.demandeId) && Objects.equals(categoriePieceId, that.categoriePieceId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(demandeId, categoriePieceId);
        }
    }
}
