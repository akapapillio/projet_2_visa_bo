package com.project.VISA.models;

import jakarta.persistence.*;

@Entity
@Table(name = "status_dm")
public class StatusDm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status_dm")
    private Long id;

    @Column(name = "status_dm", length = 50)
    private String status;

    @Column(name = "observation", length = 250)
    private String observation;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getObservation() { return observation; }
    public void setObservation(String observation) { this.observation = observation; }
}
