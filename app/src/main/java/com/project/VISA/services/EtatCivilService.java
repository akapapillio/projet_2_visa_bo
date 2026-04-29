package com.project.VISA.services;

import com.project.VISA.models.EtatCivil;
import com.project.VISA.repositories.EtatCivilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EtatCivilService {

    @Autowired
    private EtatCivilRepository etatCivilRepository;

    public List<EtatCivil> findAll() {
        return etatCivilRepository.findAll();
    }

    public Optional<EtatCivil> findById(Long id) {
        return etatCivilRepository.findById(id);
    }

    @Transactional
    public EtatCivil save(EtatCivil etatCivil) {
        return etatCivilRepository.save(etatCivil);
    }

    @Transactional
    public Optional<EtatCivil> update(Long id, EtatCivil updated) {
        return etatCivilRepository.findById(id).map(existing -> {
            // Dans le nouveau schéma, EtatCivil est une liaison technique vers Demandeur.
            // On ne met à jour que le lien si nécessaire.
            existing.setDemandeur(updated.getDemandeur());
            return etatCivilRepository.save(existing);
        });
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (etatCivilRepository.existsById(id)) {
            etatCivilRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
