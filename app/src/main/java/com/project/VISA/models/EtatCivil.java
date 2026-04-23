package com.project.VISA.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "etat_civil")
public class EtatCivil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_etat_civil")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_demandeur", nullable = false)
    private Demandeur demandeur;

    @Column(name = "nom", length = 255)
    private String nom;

    @Column(name = "prenoms", length = 255)
    private String prenoms;

    @Column(name = "nom_jeune_fille", length = 255)
    private String nomJeuneFille;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Column(name = "situation_famille", length = 100)
    private String situationFamille;

    @Column(name = "nationalite", length = 100)
    private String nationalite;

    @Column(name = "domicile_habituel", length = 500)
    private String domicileHabituel;

    @Column(name = "profession", length = 255)
    private String profession;

    @Column(name = "employeur", length = 255)
    private String employeur;

    @Column(name = "adresse_employeur", length = 500)
    private String adresseEmployeur;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Demandeur getDemandeur() { return demandeur; }
    public void setDemandeur(Demandeur demandeur) { this.demandeur = demandeur; }

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
