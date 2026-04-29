package com.project.VISA.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.VISA.dtos.PieceRequest;
import com.project.VISA.dtos.PieceResponse;
import com.project.VISA.models.Piece;
import com.project.VISA.repositories.CategoriePieceRepository;
import com.project.VISA.repositories.PieceRepository;

@Service
public class PieceService {

    private final PieceRepository pieceRepository;
    private final DemandeurService demandeurService;
    private final CategoriePieceRepository categoriePieceRepository;

    public PieceService(
            PieceRepository pieceRepository,
            DemandeurService demandeurService,
            CategoriePieceRepository categoriePieceRepository) {
        this.pieceRepository = pieceRepository;
        this.demandeurService = demandeurService;
        this.categoriePieceRepository = categoriePieceRepository;
    }

    public List<PieceResponse> findAll() {
        return pieceRepository.findAll().stream().map(this::toResponse).toList();
    }

    public PieceResponse findById(Long id) {
        return toResponse(findEntity(id));
    }

    public List<PieceResponse> findByDemandeur(Long demandeurId) {
        return pieceRepository.findByDemandeurId(demandeurId).stream().map(this::toResponse).toList();
    }

    public PieceResponse create(PieceRequest request) {
        Piece piece = new Piece();
        applyRequest(request, piece);
        return toResponse(pieceRepository.save(piece));
    }

    public PieceResponse update(Long id, PieceRequest request) {
        Piece piece = findEntity(id);
        applyRequest(request, piece);
        return toResponse(pieceRepository.save(piece));
    }

    public void delete(Long id) {
        pieceRepository.delete(findEntity(id));
    }

    public Piece findEntity(Long id) {
        return pieceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Piece introuvable: " + id));
    }

    private void applyRequest(PieceRequest request, Piece piece) {
        piece.setFichierPath(request.getFichierPath());
        piece.setDateUpload(request.getDateUpload() != null ? request.getDateUpload() : LocalDate.now());
        piece.setValide(request.getValide() != null ? request.getValide() : Boolean.FALSE);
        piece.setDemandeur(demandeurService.findEntity(request.getDemandeurId()));

        var categorie = categoriePieceRepository.findById(request.getCategoriePieceId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Categorie piece introuvable: " + request.getCategoriePieceId()));
        piece.setCategoriePiece(categorie);
    }

    private PieceResponse toResponse(Piece piece) {
        PieceResponse response = new PieceResponse();
        response.setId(piece.getId());
        response.setFichierPath(piece.getFichierPath());
        response.setDateUpload(piece.getDateUpload());
        response.setValide(piece.getValide());
        response.setDemandeurId(piece.getDemandeur().getId());
        response.setCategoriePieceId(piece.getCategoriePiece().getId());
        response.setCategoriePiece(piece.getCategoriePiece().getLibelle());
        return response;
    }
}
