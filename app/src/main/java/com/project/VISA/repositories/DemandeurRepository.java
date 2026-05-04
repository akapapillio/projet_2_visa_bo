package com.project.VISA.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.VISA.models.Demandeur;

public interface DemandeurRepository extends JpaRepository<Demandeur, Long> {
}
