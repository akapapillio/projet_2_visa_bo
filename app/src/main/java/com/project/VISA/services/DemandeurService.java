package com.project.VISA.services;

import com.project.VISA.models.Demandeur;
import com.project.VISA.repositories.DemandeurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DemandeurService {

    @Autowired
    private DemandeurRepository demandeurRepository;

    public List<Demandeur> findAll() {
        return demandeurRepository.findAll();
    }

    public Optional<Demandeur> findById(Long id) {
        return demandeurRepository.findById(id);
    }

    @Transactional
    public Demandeur save(Demandeur demandeur) {
        return demandeurRepository.save(demandeur);
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (demandeurRepository.existsById(id)) {
            demandeurRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
