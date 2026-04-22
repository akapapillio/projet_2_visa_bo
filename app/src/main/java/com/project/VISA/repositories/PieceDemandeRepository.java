package com.project.VISA.repositories;

import com.project.VISA.models.PieceDemande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PieceDemandeRepository extends JpaRepository<PieceDemande, PieceDemande.PieceDemandeId> {
}
