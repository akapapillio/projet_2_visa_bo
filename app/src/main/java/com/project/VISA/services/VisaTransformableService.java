package com.project.VISA.services;

import com.project.VISA.models.VisaTransformable;
import com.project.VISA.repositories.VisaTransformableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VisaTransformableService {

    @Autowired
    private VisaTransformableRepository visaTransformableRepository;

    public List<VisaTransformable> findAll() {
        return visaTransformableRepository.findAll();
    }

    public Optional<VisaTransformable> findById(Long id) {
        return visaTransformableRepository.findById(id);
    }

    @Transactional
    public VisaTransformable save(VisaTransformable visa) {
        return visaTransformableRepository.save(visa);
    }

    @Transactional
    public Optional<VisaTransformable> update(Long id, VisaTransformable updated) {
        return visaTransformableRepository.findById(id).map(existing -> {
            existing.setNumeroVisa(updated.getNumeroVisa());
            existing.setDateDelivrance(updated.getDateDelivrance());
            existing.setDateExpiration(updated.getDateExpiration());
            return visaTransformableRepository.save(existing);
        });
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (visaTransformableRepository.existsById(id)) {
            visaTransformableRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
