package com.project.VISA.repositories;

import com.project.VISA.models.PieceDemande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PieceDemandeRepository extends JpaRepository<PieceDemande, PieceDemande.PieceDemandeId> {
    @Query("SELECT pd FROM PieceDemande pd WHERE pd.id.typeDmId = :typeDmId")
    List<PieceDemande> findByTypeDmId(@Param("typeDmId") Long typeDmId);
}
