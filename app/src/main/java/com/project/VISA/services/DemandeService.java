package com.project.VISA.services;

import com.project.VISA.dtos.DemandeDTO;
import com.project.VISA.models.*;
import com.project.VISA.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DemandeService {

    @Autowired private DemandeRepository demandeRepository;
    @Autowired private DemandeurRepository demandeurRepository;
    @Autowired private PasseportRepository passeportRepository;
    @Autowired private TypeDemandeRepository typeDemandeRepository;
    @Autowired private StatusDmRepository statusDmRepository;
    @Autowired private NationaliteRepository nationaliteRepository;
    @Autowired private SituationFamRepository situationFamRepository;
    @Autowired private PieceRepository pieceRepository;
    @Autowired private TypeVisaRepository typeVisaRepository;
    @Autowired private VisaRepository visaRepository;

    // ─── CREATE ───────────────────────────────────────────────────────────────

    @Transactional
    public Demande creerNouvelleDemande(DemandeDTO dto) {
        Demandeur demandeur;

        // 1. Gérer le Demandeur (Existant ou Nouveau)
        if (dto.getIdDemandeur() != null) {
            demandeur = demandeurRepository.findById(dto.getIdDemandeur())
                    .orElseThrow(() -> new RuntimeException("Demandeur non trouvé avec l'id : " + dto.getIdDemandeur()));
        } else {
            demandeur = new Demandeur();
            demandeur.setNom(dto.getLastName());
            demandeur.setPrenom(dto.getFirstNames());
            demandeur.setDateNaissance(dto.getBirthDate());
            
            Nationalite nat = nationaliteRepository.findAll().stream()
                    .filter(n -> n.getNom().equalsIgnoreCase(dto.getNationality()))
                    .findFirst().orElse(null);
            demandeur.setNationalite(nat);

            SituationFam sit = situationFamRepository.findAll().stream()
                    .filter(s -> s.getLibelle().equalsIgnoreCase(dto.getMaritalStatus()))
                    .findFirst().orElse(null);
            demandeur.setSituationFamille(sit);

            // Piece par défaut
            Piece p = pieceRepository.findAll().stream().findFirst().orElse(null);
            demandeur.setPiecePrincipale(p);
            
            demandeur = demandeurRepository.save(demandeur);
        }

        // 2. Gérer le Passeport
        Passeport passeport = new Passeport();
        passeport.setDemandeur(demandeur);
        passeportRepository.save(passeport);

        // 3. Gérer la Demande
        Demande demande = new Demande();
        demande.setDemandeur(demandeur);

        TypeDemande type = typeDemandeRepository.findAll().stream()
                .filter(t -> t.getNom().equalsIgnoreCase(dto.getTypeDemande()))
                .findFirst().orElseThrow(() -> new RuntimeException("Type de demande inconnu : " + dto.getTypeDemande()));
        demande.setTypeDemande(type);

        // Statut Initial "CREE"
        StatusDm status = statusDmRepository.findAll().stream()
                .filter(s -> s.getStatus().equals("CREE"))
                .findFirst().orElse(null);
        demande.setStatus(status);

        demande = demandeRepository.save(demande);

        // 4. Déclencher le Vérificateur (Simple constat par défaut pour ce sprint)
        declencherVerificateur(demande, dto);

        return demandeRepository.save(demande);
    }

    private void declencherVerificateur(Demande demande, DemandeDTO dto) {
        StringBuilder constat = new StringBuilder();
        
        // Exemple de logique de vérification simple basée sur les booleans du DTO
        if (!dto.isaFourniPhotos()) constat.append("Manque Photos; ");
        if (!dto.isaFourniCopiePasseport()) constat.append("Manque Copie Passeport; ");
        
        if (constat.length() > 0) {
            StatusDm currentStatus = demande.getStatus();
            if (currentStatus != null) {
                currentStatus.setObservation("Constat : " + constat.toString());
                statusDmRepository.save(currentStatus);
            }
        }
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
        // Le Sprint 2 restreint l'UPDATE sur les anciennes données.
        // Mais pour les nouvelles créations internes, on peut garder la méthode.
        return demandeRepository.findById(id).map(existing -> {
            // Logique de mise à jour simplifiée pour le nouveau schéma
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
