package com.project.VISA.models;

import jakarta.persistence.*;

@Entity
@Table(name = "visa_transformable")
public class VisaTransformable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_visa_transformable")
    private Long id;

    @Column(name = "num_visa", length = 50)
    private String numVisa;

    @ManyToOne
    @JoinColumn(name = "id_passeport", nullable = false)
    private Passeport passeport;

    @ManyToOne
    @JoinColumn(name = "id_demandeur", nullable = false)
    private Demandeur demandeur;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNumVisa() { return numVisa; }
    public void setNumVisa(String numVisa) { this.numVisa = numVisa; }
    public Passeport getPasseport() { return passeport; }
    public void setPasseport(Passeport passeport) { this.passeport = passeport; }
    public Demandeur getDemandeur() { return demandeur; }
    public void setDemandeur(Demandeur demandeur) { this.demandeur = demandeur; }
}
