package com.project.VISA.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "passeport")
public class Passeport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_passeport")
    private Long id;

    @Column(name = "num_passeport")
    private Integer numPasseport;

    @Column(name = "date_expiration")
    private LocalDate dateExpiration;

    @Column(name = "date_delivrance", length = 50)
    private String dateDelivrance; // VARCHAR in SQL

    @ManyToOne
    @JoinColumn(name = "id_demandeur", nullable = false)
    private Demandeur demandeur;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getNumPasseport() { return numPasseport; }
    public void setNumPasseport(Integer numPasseport) { this.numPasseport = numPasseport; }

    public LocalDate getDateExpiration() { return dateExpiration; }
    public void setDateExpiration(LocalDate dateExpiration) { this.dateExpiration = dateExpiration; }

    public String getDateDelivrance() { return dateDelivrance; }
    public void setDateDelivrance(String dateDelivrance) { this.dateDelivrance = dateDelivrance; }

    public Demandeur getDemandeur() { return demandeur; }
    public void setDemandeur(Demandeur demandeur) { this.demandeur = demandeur; }
}
