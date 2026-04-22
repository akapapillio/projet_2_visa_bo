package com.project.VISA.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "demandeur")
public class Demandeur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_demandeur")
    private Long id;

    @Column(name = "nom", length = 50, nullable = false)
    private String nom;

    @Column(name = "prenom", length = 50)
    private String prenom;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Column(name = "lieu_naissance", length = 50)
    private String lieuNaissance;

    @ManyToOne
    @JoinColumn(name = "id_piece", nullable = false)
    private Piece piecePrincipale;

    @ManyToOne
    @JoinColumn(name = "id_situation_fam", nullable = false)
    private SituationFam situationFamille;

    @ManyToOne
    @JoinColumn(name = "id_nationalite", nullable = false)
    private Nationalite nationalite;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    public String getLieuNaissance() { return lieuNaissance; }
    public void setLieuNaissance(String lieuNaissance) { this.lieuNaissance = lieuNaissance; }

    public Piece getPiecePrincipale() { return piecePrincipale; }
    public void setPiecePrincipale(Piece piecePrincipale) { this.piecePrincipale = piecePrincipale; }

    public SituationFam getSituationFamille() { return situationFamille; }
    public void setSituationFamille(SituationFam situationFamille) { this.situationFamille = situationFamille; }

    public Nationalite getNationalite() { return nationalite; }
    public void setNationalite(Nationalite nationalite) { this.nationalite = nationalite; }
}
