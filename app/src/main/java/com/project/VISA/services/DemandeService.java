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
import java.util.Optional;

@Service
public class DemandeService {

    @Autowired
    private DemandeRepository demandeRepository;

    // ─── CREATE ───────────────────────────────────────────────────────────────

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

    // ─── READ ─────────────────────────────────────────────────────────────────

    public List<Demande> findAll() {
        return demandeRepository.findAll();
    }

    public Optional<Demande> findById(Long id) {
        return demandeRepository.findById(id);
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────

    @Transactional
    public Optional<Demande> update(Long id, DemandeDTO dto) {
        return demandeRepository.findById(id).map(existing -> {
            existing.setTypeDemande(dto.getTypeDemande());
            existing.setCategorieVisa(dto.getCategorieVisa());

            // Update EtatCivil
            EtatCivil ec = existing.getEtatCivil();
            if (ec == null) ec = new EtatCivil();
            ec.setNom(dto.getLastName());
            ec.setPrenoms(dto.getFirstNames());
            ec.setNomJeuneFille(dto.getMaidenName());
            ec.setDateNaissance(dto.getBirthDate());
            ec.setSituationFamille(dto.getMaritalStatus());
            ec.setNationalite(dto.getNationality());
            ec.setDomicileHabituel(dto.getHomeAddress());
            ec.setProfession(dto.getOccupation());
            ec.setEmployeur(dto.getEmployerName());
            ec.setAdresseEmployeur(dto.getEmployerAddress());
            existing.setEtatCivil(ec);

            // Update VisaTransformable
            VisaTransformable vt = existing.getVisaTransformable();
            if (vt == null) vt = new VisaTransformable();
            vt.setNumeroVisa(dto.getNumeroVisaPrcd());
            vt.setDateDelivrance(dto.getDateDelivranceVisaPrcd());
            vt.setDateExpiration(dto.getDateExpirationVisaPrcd());
            existing.setVisaTransformable(vt);

            // Update booleans
            existing.setaFourniPhotos(dto.isaFourniPhotos());
            existing.setaFourniNoticeRenseignement(dto.isaFourniNoticeRenseignement());
            existing.setaFourniDemandeMinistre(dto.isaFourniDemandeMinistre());
            existing.setaFourniCopieVisa(dto.isaFourniCopieVisa());
            existing.setaFourniCopiePasseport(dto.isaFourniCopiePasseport());
            existing.setaFourniCopieCarteResident(dto.isaFourniCopieCarteResident());
            existing.setaFourniCertificatResidence(dto.isaFourniCertificatResidence());
            existing.setaFourniExtraitCasierJudiciaire(dto.isaFourniExtraitCasierJudiciaire());
            existing.setaFourniStatutSociete(dto.isaFourniStatutSociete());
            existing.setaFourniExtraitRC(dto.isaFourniExtraitRC());
            existing.setaFourniCarteFiscale(dto.isaFourniCarteFiscale());
            existing.setaFourniAutorisationEmploi(dto.isaFourniAutorisationEmploi());
            existing.setaFourniAttestationEmploi(dto.isaFourniAttestationEmploi());

            return demandeRepository.save(existing);
        });
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────

    @Transactional
    public boolean deleteById(Long id) {
        if (demandeRepository.existsById(id)) {
            demandeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

