package com.project.VISA.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.VISA.models.Piece;

public interface PieceRepository extends JpaRepository<Piece, Long> {

    List<Piece> findByDemandeurId(Long demandeurId);

    boolean existsByDemandeurIdAndCategoriePieceIdAndValideTrue(Long demandeurId, Long categoriePieceId);
}
