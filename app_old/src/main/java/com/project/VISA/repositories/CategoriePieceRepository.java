package com.project.VISA.repositories;

import com.project.VISA.models.CategoriePiece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriePieceRepository extends JpaRepository<CategoriePiece, Long> {
}
