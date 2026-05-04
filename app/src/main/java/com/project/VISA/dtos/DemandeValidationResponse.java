package com.project.VISA.dtos;

import java.util.ArrayList;
import java.util.List;

public class DemandeValidationResponse {

    private Long demandeId;
    private boolean valide;
    private List<String> piecesManquantes = new ArrayList<>();
    private List<String> objetsManquants = new ArrayList<>();

    public Long getDemandeId() {
        return demandeId;
    }

    public void setDemandeId(Long demandeId) {
        this.demandeId = demandeId;
    }

    public boolean isValide() {
        return valide;
    }

    public void setValide(boolean valide) {
        this.valide = valide;
    }

    public List<String> getPiecesManquantes() {
        return piecesManquantes;
    }

    public void setPiecesManquantes(List<String> piecesManquantes) {
        this.piecesManquantes = piecesManquantes;
    }

    public List<String> getObjetsManquants() {
        return objetsManquants;
    }

    public void setObjetsManquants(List<String> objetsManquants) {
        this.objetsManquants = objetsManquants;
    }
}
