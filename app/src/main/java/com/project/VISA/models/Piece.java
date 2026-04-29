package com.project.VISA.models;

import jakarta.persistence.*;

@Entity
@Table(name = "piece")
public class Piece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_piece")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_categorie_piece", nullable = false)
    private CategoriePiece categoriePiece;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public CategoriePiece getCategoriePiece() { return categoriePiece; }
    public void setCategoriePiece(CategoriePiece categoriePiece) { this.categoriePiece = categoriePiece; }
}
