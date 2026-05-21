package com.project.VISA.dtos;

import java.time.LocalDateTime;

public class DemandeResponse {

    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long demandeurId;
    private String nomDemandeur;
    private Long statusId;
    private String status;
    private Long typeDemandeId;
    private String typeDemande;
    private Long typeVisaId;
    private String typeVisa;

    // --- Photo & Signature ---
    private String photoUrl;
    private LocalDateTime photoUploadDate;
    private String signatureUrl;
    private LocalDateTime signatureUploadDate;
    private String raisonRefus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getDemandeurId() {
        return demandeurId;
    }

    public void setDemandeurId(Long demandeurId) {
        this.demandeurId = demandeurId;
    }

    public String getNomDemandeur() {
        return nomDemandeur;
    }

    public void setNomDemandeur(String nomDemandeur) {
        this.nomDemandeur = nomDemandeur;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTypeDemandeId() {
        return typeDemandeId;
    }

    public void setTypeDemandeId(Long typeDemandeId) {
        this.typeDemandeId = typeDemandeId;
    }

    public String getTypeDemande() {
        return typeDemande;
    }

    public void setTypeDemande(String typeDemande) {
        this.typeDemande = typeDemande;
    }

    public Long getTypeVisaId() {
        return typeVisaId;
    }

    public void setTypeVisaId(Long typeVisaId) {
        this.typeVisaId = typeVisaId;
    }

    public String getTypeVisa() {
        return typeVisa;
    }

    public void setTypeVisa(String typeVisa) {
        this.typeVisa = typeVisa;
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
