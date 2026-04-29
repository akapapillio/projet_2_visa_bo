package com.project.VISA.models;

import jakarta.persistence.*;

@Entity
@Table(name = "nationalite")
public class Nationalite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nationalite")
    private Long id;

    @Column(name = "nationalite", length = 50, nullable = false)
    private String nom;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
}
