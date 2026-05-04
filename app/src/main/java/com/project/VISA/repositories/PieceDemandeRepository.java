package com.project.VISA.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.VISA.models.PieceDemande;
import com.project.VISA.models.PieceDemandeId;

public interface PieceDemandeRepository extends JpaRepository<PieceDemande, PieceDemandeId> {

    List<PieceDemande> findByTypeDemandeId(Long typeDemandeId);
}
