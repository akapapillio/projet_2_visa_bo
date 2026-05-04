package com.project.VISA.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.VISA.models.EtatCivil;

public interface EtatCivilRepository extends JpaRepository<EtatCivil, Long> {

    boolean existsByDemandeurId(Long demandeurId);

    List<EtatCivil> findByDemandeurId(Long demandeurId);
}
