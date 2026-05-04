package com.project.VISA.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.VISA.dtos.PieceDemandeRequest;
import com.project.VISA.models.PieceDemande;
import com.project.VISA.models.PieceDemandeId;
import com.project.VISA.repositories.CategoriePieceRepository;
import com.project.VISA.repositories.PieceDemandeRepository;
import com.project.VISA.repositories.TypeDemandeObjetMetierObligatoireRepository;
import com.project.VISA.repositories.TypeDemandeRepository;

@Service
public class RuleService {

    private final PieceDemandeRepository pieceDemandeRepository;
    private final TypeDemandeRepository typeDemandeRepository;
    private final CategoriePieceRepository categoriePieceRepository;
    private final TypeDemandeObjetMetierObligatoireRepository objetObligatoireRepository;

    public RuleService(
            PieceDemandeRepository pieceDemandeRepository,
            TypeDemandeRepository typeDemandeRepository,
            CategoriePieceRepository categoriePieceRepository,
            TypeDemandeObjetMetierObligatoireRepository objetObligatoireRepository) {
        this.pieceDemandeRepository = pieceDemandeRepository;
        this.typeDemandeRepository = typeDemandeRepository;
        this.categoriePieceRepository = categoriePieceRepository;
        this.objetObligatoireRepository = objetObligatoireRepository;
    }

    public PieceDemande createPieceRule(PieceDemandeRequest request) {
        var typeDemande = typeDemandeRepository.findById(request.getTypeDemandeId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Type demande introuvable: " + request.getTypeDemandeId()));
        var categorie = categoriePieceRepository.findById(request.getCategoriePieceId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Categorie piece introuvable: " + request.getCategoriePieceId()));

        PieceDemande pieceDemande = new PieceDemande();
        pieceDemande.setId(new PieceDemandeId(typeDemande.getId(), categorie.getId()));
        pieceDemande.setTypeDemande(typeDemande);
        pieceDemande.setCategoriePiece(categorie);
        return pieceDemandeRepository.save(pieceDemande);
    }

    public void deletePieceRule(Long typeDemandeId, Long categoriePieceId) {
        PieceDemandeId id = new PieceDemandeId(typeDemandeId, categoriePieceId);
        if (!pieceDemandeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Regle piece/type demande introuvable");
        }
        pieceDemandeRepository.deleteById(id);
    }

    public List<PieceDemande> getPieceRules(Long typeDemandeId) {
        return pieceDemandeRepository.findByTypeDemandeId(typeDemandeId);
    }

    public List<String> getRequiredObjectNames(Long typeDemandeId) {
        return objetObligatoireRepository.findByTypeDemandeIdAndObligatoireTrue(typeDemandeId)
                .stream()
                .map(rule -> rule.getTypeObjet().getNom())
                .toList();
    }
}
