package com.project.VISA.models;

import jakarta.persistence.*;

@Entity
@Table(name = "type_demande")
public class TypeDemande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_dm")
    private Long id;

    @Column(name = "nom_type_dm", length = 50)
    private String nom;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
}
