package com.project.VISA.models;

import jakarta.persistence.*;

@Entity
@Table(name = "type_visa")
public class TypeVisa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_visa")
    private Long id;

    @Column(name = "libelle", length = 50, nullable = false)
    private String libelle;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
}
