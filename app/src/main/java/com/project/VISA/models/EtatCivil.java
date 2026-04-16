package com.project.VISA.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "etat_civil")
public class EtatCivil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenoms;
    private String nomJeuneFille;
    private LocalDate dateNaissance;
    private String situationFamille;
    private String nationalite;
    private String domicileHabituel;
    private String profession;
    private String employeur;
    private String adresseEmployeur;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenoms() { return prenoms; }
    public void setPrenoms(String prenoms) { this.prenoms = prenoms; }
    public String getNomJeuneFille() { return nomJeuneFille; }
    public void setNomJeuneFille(String nomJeuneFille) { this.nomJeuneFille = nomJeuneFille; }
    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }
    public String getSituationFamille() { return situationFamille; }
    public void setSituationFamille(String situationFamille) { this.situationFamille = situationFamille; }
    public String getNationalite() { return nationalite; }
    public void setNationalite(String nationalite) { this.nationalite = nationalite; }
    public String getDomicileHabituel() { return domicileHabituel; }
    public void setDomicileHabituel(String domicileHabituel) { this.domicileHabituel = domicileHabituel; }
    public String getProfession() { return profession; }
    public void setProfession(String profession) { this.profession = profession; }
    public String getEmployeur() { return employeur; }
    public void setEmployeur(String employeur) { this.employeur = employeur; }
    public String getAdresseEmployeur() { return adresseEmployeur; }
    public void setAdresseEmployeur(String adresseEmployeur) { this.adresseEmployeur = adresseEmployeur; }
}
