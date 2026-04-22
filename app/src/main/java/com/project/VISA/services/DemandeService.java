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
        // 1. Gérer le Demandeur
        Demandeur demandeur = new Demandeur();
        demandeur.setNom(dto.getLastName());
        demandeur.setPrenom(dto.getFirstNames());
        demandeur.setDateNaissance(dto.getBirthDate());
        
        // Lookups
        Nationalite nat = nationaliteRepository.findAll().stream()
                .filter(n -> n.getNom().equalsIgnoreCase(dto.getNationality()))
                .findFirst().orElse(nationaliteRepository.findAll().get(0));
        demandeur.setNationalite(nat);

        SituationFam sit = situationFamRepository.findAll().stream()
                .filter(s -> s.getLibelle().equalsIgnoreCase(dto.getMaritalStatus()))
                .findFirst().orElse(situationFamRepository.findAll().get(0));
        demandeur.setSituationFamille(sit);

        // Piece par défaut (requis par le schéma SQL pour le demandeur)
        Piece p = pieceRepository.findAll().stream().findFirst().orElse(null);
        if (p == null) {
            // Logique de secours si aucune pièce n'existe encore
            // Normalement géré par DataInitializer
        }
        demandeur.setPiecePrincipale(p);
        
        demandeur = demandeurRepository.save(demandeur);

        // 2. Gérer le Passeport
        Passeport passeport = new Passeport();
        passeport.setDemandeur(demandeur);
        // On pourrait ajouter des champs au DTO pour le passeport plus tard
        passeportRepository.save(passeport);

        // 3. Gérer la Demande
        Demande demande = new Demande();
        demande.setDemandeur(demandeur);

        TypeDemande type = typeDemandeRepository.findAll().stream()
                .filter(t -> t.getNom().equalsIgnoreCase(dto.getTypeDemande()))
                .findFirst().orElse(typeDemandeRepository.findAll().get(0));
        demande.setTypeDemande(type);

        StatusDm status = statusDmRepository.findAll().stream()
                .filter(s -> s.getStatus().equals("CREE"))
                .findFirst().orElse(statusDmRepository.findAll().get(0));
        demande.setStatus(status);

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
