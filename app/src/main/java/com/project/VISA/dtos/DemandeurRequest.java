package com.project.VISA.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DemandeurRequest {

    @NotBlank
    @Size(max = 100)
    private String nom;

    @Size(max = 100)
    private String prenom;

    private LocalDate dateNaissance;

    @Size(max = 120)
    private String lieuNaissance;

    @NotNull
    private Long situationFamilialeId;

    @NotNull
    private Long nationaliteId;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public Long getSituationFamilialeId() {
        return situationFamilialeId;
    }

    public void setSituationFamilialeId(Long situationFamilialeId) {
        this.situationFamilialeId = situationFamilialeId;
    }

    public Long getNationaliteId() {
        return nationaliteId;
    }

    public void setNationaliteId(Long nationaliteId) {
        this.nationaliteId = nationaliteId;
    }
}
