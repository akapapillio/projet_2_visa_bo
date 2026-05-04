package com.project.VISA.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.VISA.models.Passeport;

public interface PasseportRepository extends JpaRepository<Passeport, Long> {

    boolean existsByDemandeurId(Long demandeurId);

    List<Passeport> findByDemandeurId(Long demandeurId);
}
