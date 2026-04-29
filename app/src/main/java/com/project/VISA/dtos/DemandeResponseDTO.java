package com.project.VISA.dtos;

import java.time.LocalDate;
import java.util.List;

public class DemandeResponseDTO {
    private Long id;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String demandeurNom;
    private String demandeurPrenom;
    private String typeDemandeName;
    private String statusName;
    private String employeur;
    private String profession;
    private String occupationOuEmployeur;
    private List<DemandePieceStatusDTO> pieces;

    public DemandeResponseDTO() {}

    public DemandeResponseDTO(Long id, LocalDate createdAt, LocalDate updatedAt,
                             String demandeurNom, String demandeurPrenom,
                             String typeDemandeName, String statusName,
                             String employeur, String profession) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.demandeurNom = demandeurNom;
        this.demandeurPrenom = demandeurPrenom;
        this.typeDemandeName = typeDemandeName;
        this.statusName = statusName;
        this.employeur = employeur;
        this.profession = profession;
        this.occupationOuEmployeur = (employeur != null ? employeur : "") +
                                    (profession != null ? " / " + profession : "");
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }

    public LocalDate getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDate updatedAt) { this.updatedAt = updatedAt; }

    public String getDemandeurNom() { return demandeurNom; }
    public void setDemandeurNom(String demandeurNom) { this.demandeurNom = demandeurNom; }

    public String getDemandeurPrenom() { return demandeurPrenom; }
    public void setDemandeurPrenom(String demandeurPrenom) { this.demandeurPrenom = demandeurPrenom; }

    public String getTypeDemandeName() { return typeDemandeName; }
    public void setTypeDemandeName(String typeDemandeName) { this.typeDemandeName = typeDemandeName; }

    public String getStatusName() { return statusName; }
    public void setStatusName(String statusName) { this.statusName = statusName; }

    public String getEmployeur() { return employeur; }
    public void setEmployeur(String employeur) { this.employeur = employeur; }

    public String getProfession() { return profession; }
    public void setProfession(String profession) { this.profession = profession; }

    public String getOccupationOuEmployeur() { return occupationOuEmployeur; }
    public void setOccupationOuEmployeur(String occupationOuEmployeur) { this.occupationOuEmployeur = occupationOuEmployeur; }

    public List<DemandePieceStatusDTO> getPieces() { return pieces; }
    public void setPieces(List<DemandePieceStatusDTO> pieces) { this.pieces = pieces; }
}
