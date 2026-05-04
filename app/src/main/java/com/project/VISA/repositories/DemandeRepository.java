package com.project.VISA.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.VISA.models.Demande;

public interface DemandeRepository extends JpaRepository<Demande, Long> {

    List<Demande> findByDemandeurId(Long demandeurId);
}
