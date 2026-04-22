package com.project.VISA.models;

import jakarta.persistence.*;

@Entity
@Table(name = "carte_resident")
public class CarteResident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carte_resident")
    private Long id;

    @Column(name = "num")
    private Integer num;

    @ManyToOne
    @JoinColumn(name = "id_demandeur", nullable = false)
    private Demandeur demandeur;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getNum() { return num; }
    public void setNum(Integer num) { this.num = num; }
    public Demandeur getDemandeur() { return demandeur; }
    public void setDemandeur(Demandeur demandeur) { this.demandeur = demandeur; }
}
