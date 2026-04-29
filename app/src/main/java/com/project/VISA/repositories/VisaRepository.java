package com.project.VISA.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.VISA.models.Visa;

public interface VisaRepository extends JpaRepository<Visa, Long> {

    boolean existsByDemandeurId(Long demandeurId);

    List<Visa> findByDemandeurId(Long demandeurId);
}
