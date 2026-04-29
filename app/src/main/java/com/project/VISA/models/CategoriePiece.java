package com.project.VISA.models;

import jakarta.persistence.*;

@Entity
@Table(name = "categorie_piece")
public class CategoriePiece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categorie_piece")
    private Long id;

    @Column(name = "libelle", length = 50)
    private String libelle;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
}
