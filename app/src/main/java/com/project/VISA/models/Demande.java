package com.project.VISA.models;

import com.project.VISA.models.enums.CategorieVisa;
import com.project.VISA.models.enums.TypeDemande;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "demandes")
public class Demande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TypeDemande typeDemande;

    @Enumerated(EnumType.STRING)
    private CategorieVisa categorieVisa;

    private LocalDate dateDemande;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "etat_civil_id", referencedColumnName = "id")
    private EtatCivil etatCivil;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "visa_transformable_id", referencedColumnName = "id")
    private VisaTransformable visaTransformable;

    // Pièces Communes
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


    @PrePersist
    protected void onCreate() {
        if (dateDemande == null) {
            this.dateDemande = LocalDate.now();
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public TypeDemande getTypeDemande() { return typeDemande; }
    public void setTypeDemande(TypeDemande typeDemande) { this.typeDemande = typeDemande; }

    public CategorieVisa getCategorieVisa() { return categorieVisa; }
    public void setCategorieVisa(CategorieVisa categorieVisa) { this.categorieVisa = categorieVisa; }

    public LocalDate getDateDemande() { return dateDemande; }
    public void setDateDemande(LocalDate dateDemande) { this.dateDemande = dateDemande; }

    public EtatCivil getEtatCivil() { return etatCivil; }
    public void setEtatCivil(EtatCivil etatCivil) { this.etatCivil = etatCivil; }

    public VisaTransformable getVisaTransformable() { return visaTransformable; }
    public void setVisaTransformable(VisaTransformable visaTransformable) { this.visaTransformable = visaTransformable; }

    // Boolean getters and setters (Communes)
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

    // Boolean getters and setters (Investisseur)
    public boolean isaFourniStatutSociete() { return aFourniStatutSociete; }
    public void setaFourniStatutSociete(boolean aFourniStatutSociete) { this.aFourniStatutSociete = aFourniStatutSociete; }

    public boolean isaFourniExtraitRC() { return aFourniExtraitRC; }
    public void setaFourniExtraitRC(boolean aFourniExtraitRC) { this.aFourniExtraitRC = aFourniExtraitRC; }

    public boolean isaFourniCarteFiscale() { return aFourniCarteFiscale; }
    public void setaFourniCarteFiscale(boolean aFourniCarteFiscale) { this.aFourniCarteFiscale = aFourniCarteFiscale; }

    // Boolean getters and setters (Travailleur)
    public boolean isaFourniAutorisationEmploi() { return aFourniAutorisationEmploi; }
    public void setaFourniAutorisationEmploi(boolean aFourniAutorisationEmploi) { this.aFourniAutorisationEmploi = aFourniAutorisationEmploi; }

    public boolean isaFourniAttestationEmploi() { return aFourniAttestationEmploi; }
    public void setaFourniAttestationEmploi(boolean aFourniAttestationEmploi) { this.aFourniAttestationEmploi = aFourniAttestationEmploi; }

}
