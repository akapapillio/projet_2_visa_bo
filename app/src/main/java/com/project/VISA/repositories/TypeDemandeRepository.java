package com.project.VISA.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.VISA.models.TypeDemande;

public interface TypeDemandeRepository extends JpaRepository<TypeDemande, Long> {

    Optional<TypeDemande> findByNom(String nom);
}
