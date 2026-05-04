package com.project.VISA.repositories;

import com.project.VISA.models.VisaTransformable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisaTransformableRepository extends JpaRepository<VisaTransformable, Long> {
    List<VisaTransformable> findByDemandeurId(Long demandeurId);
}
