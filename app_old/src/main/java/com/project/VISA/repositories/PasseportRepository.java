package com.project.VISA.repositories;

import com.project.VISA.models.Passeport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PasseportRepository extends JpaRepository<Passeport, Long> {
    List<Passeport> findByDemandeurId(Long demandeurId);
}
