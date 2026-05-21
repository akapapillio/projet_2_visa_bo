package com.project.VISA.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "demande")
public class Demande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_demande")
    private Long id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_demandeur", nullable = false)
    private Demandeur demandeur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_status_dm", nullable = false)
    private StatusDm status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_dm", nullable = false)
    private TypeDemande typeDemande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_visa")
    private TypeVisa typeVisa;

    // --- Colonnes photo ---
    @Column(name = "photo_path", length = 500)
    private String photoPath;

    @Column(name = "photo_url", length = 1000)
    private String photoUrl;

    @Column(name = "photo_upload_date")
    private LocalDateTime photoUploadDate;

    // --- Colonnes signature ---
    @Column(name = "signature_path", length = 500)
    private String signaturePath;

    @Column(name = "signature_url", length = 1000)
    private String signatureUrl;

    @Column(name = "signature_upload_date")
    private LocalDateTime signatureUploadDate;

    // --- Raison de refus ---
    @Column(name = "raison_refus", length = 1000)
    private String raisonRefus;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Demandeur getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(Demandeur demandeur) {
        this.demandeur = demandeur;
    }

    public StatusDm getStatus() {
        return status;
    }

    public void setStatus(StatusDm status) {
        this.status = status;
    }

    public TypeDemande getTypeDemande() {
        return typeDemande;
    }

    public void setTypeDemande(TypeDemande typeDemande) {
        this.typeDemande = typeDemande;
    }

    public TypeVisa getTypeVisa() {
        return typeVisa;
    }

    public void setTypeVisa(TypeVisa typeVisa) {
        this.typeVisa = typeVisa;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public LocalDateTime getPhotoUploadDate() {
        return photoUploadDate;
    }

    public void setPhotoUploadDate(LocalDateTime photoUploadDate) {
        this.photoUploadDate = photoUploadDate;
    }

    public String getSignaturePath() {
        return signaturePath;
    }

    public void setSignaturePath(String signaturePath) {
        this.signaturePath = signaturePath;
    }

    public String getSignatureUrl() {
        return signatureUrl;
    }

    public void setSignatureUrl(String signatureUrl) {
        this.signatureUrl = signatureUrl;
    }

    public LocalDateTime getSignatureUploadDate() {
        return signatureUploadDate;
    }

    public void setSignatureUploadDate(LocalDateTime signatureUploadDate) {
        this.signatureUploadDate = signatureUploadDate;
    }

    public String getRaisonRefus() {
        return raisonRefus;
    }

    public void setRaisonRefus(String raisonRefus) {
        this.raisonRefus = raisonRefus;
    }
}
