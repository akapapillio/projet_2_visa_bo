package com.project.VISA.services;

import com.project.VISA.dtos.DemandeDTO;
import com.project.VISA.models.Demande;
import com.project.VISA.models.EtatCivil;
import com.project.VISA.models.VisaTransformable;
import com.project.VISA.repositories.DemandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class DemandeService {

    @Autowired
    private DemandeRepository demandeRepository;

    @Transactional
    public Demande creerNouvelleDemande(DemandeDTO dto) {
        EtatCivil etatCivil = new EtatCivil();
        etatCivil.setNom(dto.getLastName());
        etatCivil.setPrenoms(dto.getFirstNames());
        etatCivil.setNomJeuneFille(dto.getMaidenName());
        etatCivil.setDateNaissance(dto.getBirthDate());
        etatCivil.setSituationFamille(dto.getMaritalStatus());
        etatCivil.setNationalite(dto.getNationality());
        etatCivil.setDomicileHabituel(dto.getHomeAddress());
        etatCivil.setProfession(dto.getOccupation());
        etatCivil.setEmployeur(dto.getEmployerName());
        etatCivil.setAdresseEmployeur(dto.getEmployerAddress());

        VisaTransformable visaPrev = new VisaTransformable();
        visaPrev.setNumeroVisa(dto.getNumeroVisaPrcd());
        visaPrev.setDateDelivrance(dto.getDateDelivranceVisaPrcd());
        visaPrev.setDateExpiration(dto.getDateExpirationVisaPrcd());

        Demande demande = new Demande();
        demande.setTypeDemande(dto.getTypeDemande());
        demande.setCategorieVisa(dto.getCategorieVisa());
        demande.setEtatCivil(etatCivil);
        demande.setVisaTransformable(visaPrev);

        // Map booleans
        demande.setaFourniPhotos(dto.isaFourniPhotos());
        demande.setaFourniNoticeRenseignement(dto.isaFourniNoticeRenseignement());
        demande.setaFourniDemandeMinistre(dto.isaFourniDemandeMinistre());
        demande.setaFourniCopieVisa(dto.isaFourniCopieVisa());
        demande.setaFourniCopiePasseport(dto.isaFourniCopiePasseport());
        demande.setaFourniCopieCarteResident(dto.isaFourniCopieCarteResident());
        demande.setaFourniCertificatResidence(dto.isaFourniCertificatResidence());
        demande.setaFourniExtraitCasierJudiciaire(dto.isaFourniExtraitCasierJudiciaire());

        demande.setaFourniStatutSociete(dto.isaFourniStatutSociete());
        demande.setaFourniExtraitRC(dto.isaFourniExtraitRC());
        demande.setaFourniCarteFiscale(dto.isaFourniCarteFiscale());

        demande.setaFourniAutorisationEmploi(dto.isaFourniAutorisationEmploi());
        demande.setaFourniAttestationEmploi(dto.isaFourniAttestationEmploi());

        return demandeRepository.save(demande);
    }

    public List<Demande> findAll() {
        return demandeRepository.findAll();
    }
}
