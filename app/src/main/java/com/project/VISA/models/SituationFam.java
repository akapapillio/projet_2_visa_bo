package com.project.VISA.models;

import jakarta.persistence.*;

@Entity
@Table(name = "situation_fam")
public class SituationFam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_situation_fam")
    private Long id;

    @Column(name = "situation_fam", length = 50)
    private String libelle;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
}
