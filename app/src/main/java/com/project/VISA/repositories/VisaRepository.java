package com.project.VISA.repositories;

import com.project.VISA.models.Visa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisaRepository extends JpaRepository<Visa, Long> {
    List<Visa> findByDemandeurId(Long demandeurId);
}
