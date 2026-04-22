package com.project.VISA.dtos;

import com.project.VISA.models.enums.CategorieVisa;
import com.project.VISA.models.enums.TypeDemande;
import java.time.LocalDate;

public class DemandeDTO {
    // Type d'application (lookup par nom)
    private String typeDemande;
    private String typeVisa; // Remplace CategorieVisa
    private Long idDemandeur; // Pour lier à un demandeur existant

    // Etat Civil
    private String lastName;
    private String firstNames;
    private String maidenName;
    private LocalDate birthDate;
    private String maritalStatus;
    private String nationality;
    private String homeAddress;
    private String occupation;
    private String employerName;
    private String employerAddress;

    // Visa Transformable (précédent)
    private String numeroVisaPrcd;
    private LocalDate dateDelivranceVisaPrcd;
    private LocalDate dateExpirationVisaPrcd;

    // Pièces fournies (Communes)
    private boolean aFourniPhotos;
    private boolean aFourniNoticeRenseignement;
    private boolean aFourniDemandeMinistre;
    private boolean aFourniCopieVisa;
    private boolean aFourniCopiePasseport;
    private boolean aFourniCopieCarteResident;
    private boolean aFourniCertificatResidence;
    private boolean aFourniExtraitCasierJudiciaire;

    // Pièces Investisseur
    private boolean aFourniStatutSociete;
    private boolean aFourniExtraitRC;
    private boolean aFourniCarteFiscale;

    // Pièces Travailleur
    private boolean aFourniAutorisationEmploi;
    private boolean aFourniAttestationEmploi;

    // Getters and Setters missing for brevity
    // But lombok is not in pom, so generating standard getters/setters

    public String getTypeDemande() { return typeDemande; }
    public void setTypeDemande(String typeDemande) { this.typeDemande = typeDemande; }

    public String getTypeVisa() { return typeVisa; }
    public void setTypeVisa(String typeVisa) { this.typeVisa = typeVisa; }

    public Long getIdDemandeur() { return idDemandeur; }
    public void setIdDemandeur(Long idDemandeur) { this.idDemandeur = idDemandeur; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFirstNames() { return firstNames; }
    public void setFirstNames(String firstNames) { this.firstNames = firstNames; }

    public String getMaidenName() { return maidenName; }
    public void setMaidenName(String maidenName) { this.maidenName = maidenName; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getMaritalStatus() { return maritalStatus; }
    public void setMaritalStatus(String maritalStatus) { this.maritalStatus = maritalStatus; }

    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }

    public String getHomeAddress() { return homeAddress; }
    public void setHomeAddress(String homeAddress) { this.homeAddress = homeAddress; }

    public String getOccupation() { return occupation; }
    public void setOccupation(String occupation) { this.occupation = occupation; }

    public String getEmployerName() { return employerName; }
    public void setEmployerName(String employerName) { this.employerName = employerName; }

    public String getEmployerAddress() { return employerAddress; }
    public void setEmployerAddress(String employerAddress) { this.employerAddress = employerAddress; }

    public String getNumeroVisaPrcd() { return numeroVisaPrcd; }
    public void setNumeroVisaPrcd(String numeroVisaPrcd) { this.numeroVisaPrcd = numeroVisaPrcd; }

    public LocalDate getDateDelivranceVisaPrcd() { return dateDelivranceVisaPrcd; }
    public void setDateDelivranceVisaPrcd(LocalDate dateDelivranceVisaPrcd) { this.dateDelivranceVisaPrcd = dateDelivranceVisaPrcd; }

    public LocalDate getDateExpirationVisaPrcd() { return dateExpirationVisaPrcd; }
    public void setDateExpirationVisaPrcd(LocalDate dateExpirationVisaPrcd) { this.dateExpirationVisaPrcd = dateExpirationVisaPrcd; }

    public boolean isaFourniPhotos() { return aFourniPhotos; }
    public void setaFourniPhotos(boolean aFourniPhotos) { this.aFourniPhotos = aFourniPhotos; }

    public boolean isaFourniNoticeRenseignement() { return aFourniNoticeRenseignement; }
    public void setaFourniNoticeRenseignement(boolean aFourniNoticeRenseignement) { this.aFourniNoticeRenseignement = aFourniNoticeRenseignement; }

    public boolean isaFourniDemandeMinistre() { return aFourniDemandeMinistre; }
    public void setaFourniDemandeMinistre(boolean aFourniDemandeMinistre) { this.aFourniDemandeMinistre = aFourniDemandeMinistre; }

    public boolean isaFourniCopieVisa() { return aFourniCopieVisa; }
    public void setaFourniCopieVisa(boolean aFourniCopieVisa) { this.aFourniCopieVisa = aFourniCopieVisa; }

    public boolean isaFourniCopiePasseport() { return aFourniCopiePasseport; }
    public void setaFourniCopiePasseport(boolean aFourniCopiePasseport) { this.aFourniCopiePasseport = aFourniCopiePasseport; }

    public boolean isaFourniCopieCarteResident() { return aFourniCopieCarteResident; }
    public void setaFourniCopieCarteResident(boolean aFourniCopieCarteResident) { this.aFourniCopieCarteResident = aFourniCopieCarteResident; }

    public boolean isaFourniCertificatResidence() { return aFourniCertificatResidence; }
    public void setaFourniCertificatResidence(boolean aFourniCertificatResidence) { this.aFourniCertificatResidence = aFourniCertificatResidence; }

    public boolean isaFourniExtraitCasierJudiciaire() { return aFourniExtraitCasierJudiciaire; }
    public void setaFourniExtraitCasierJudiciaire(boolean aFourniExtraitCasierJudiciaire) { this.aFourniExtraitCasierJudiciaire = aFourniExtraitCasierJudiciaire; }

    public boolean isaFourniStatutSociete() { return aFourniStatutSociete; }
    public void setaFourniStatutSociete(boolean aFourniStatutSociete) { this.aFourniStatutSociete = aFourniStatutSociete; }

    public boolean isaFourniExtraitRC() { return aFourniExtraitRC; }
    public void setaFourniExtraitRC(boolean aFourniExtraitRC) { this.aFourniExtraitRC = aFourniExtraitRC; }

    public boolean isaFourniCarteFiscale() { return aFourniCarteFiscale; }
    public void setaFourniCarteFiscale(boolean aFourniCarteFiscale) { this.aFourniCarteFiscale = aFourniCarteFiscale; }

    public boolean isaFourniAutorisationEmploi() { return aFourniAutorisationEmploi; }
    public void setaFourniAutorisationEmploi(boolean aFourniAutorisationEmploi) { this.aFourniAutorisationEmploi = aFourniAutorisationEmploi; }

    public boolean isaFourniAttestationEmploi() { return aFourniAttestationEmploi; }
    public void setaFourniAttestationEmploi(boolean aFourniAttestationEmploi) { this.aFourniAttestationEmploi = aFourniAttestationEmploi; }
}
