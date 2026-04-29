package com.project.VISA.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.VISA.models.TypeDemandeObjetMetierObligatoire;
import com.project.VISA.models.TypeDemandeObjetMetierObligatoireId;

public interface TypeDemandeObjetMetierObligatoireRepository
        extends JpaRepository<TypeDemandeObjetMetierObligatoire, TypeDemandeObjetMetierObligatoireId> {

    List<TypeDemandeObjetMetierObligatoire> findByTypeDemandeIdAndObligatoireTrue(Long typeDemandeId);
}
