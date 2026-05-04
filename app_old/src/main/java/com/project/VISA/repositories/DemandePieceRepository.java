package com.project.VISA.repositories;

import com.project.VISA.models.DemandePiece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DemandePieceRepository extends JpaRepository<DemandePiece, DemandePiece.DemandePieceId> {
    List<DemandePiece> findByDemandeId(Long demandeId);
    Optional<DemandePiece> findByDemandeIdAndCategoriePieceId(Long demandeId, Long categoriePieceId);
}
