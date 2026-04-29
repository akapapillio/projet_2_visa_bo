package com.project.VISA.repositories;

import com.project.VISA.models.EtatCivil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtatCivilRepository extends JpaRepository<EtatCivil, Long> {
}
