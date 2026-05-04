package com.project.VISA.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.VISA.models.VisaTransformable;

public interface VisaTransformableRepository extends JpaRepository<VisaTransformable, Long> {

    boolean existsByDemandeurId(Long demandeurId);

    List<VisaTransformable> findByDemandeurId(Long demandeurId);
}
