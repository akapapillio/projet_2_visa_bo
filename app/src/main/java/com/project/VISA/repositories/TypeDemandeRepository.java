package com.project.VISA.repositories;

import com.project.VISA.models.TypeDemande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeDemandeRepository extends JpaRepository<TypeDemande, Long> {
}
