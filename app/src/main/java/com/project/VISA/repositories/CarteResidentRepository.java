package com.project.VISA.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.VISA.models.CarteResident;

public interface CarteResidentRepository extends JpaRepository<CarteResident, Long> {

    boolean existsByDemandeurId(Long demandeurId);

    List<CarteResident> findByDemandeurId(Long demandeurId);
}
