package com.project.VISA.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "visa_passeport")
public class VisaPasseport {

    @EmbeddedId
    private VisaPasseportId id = new VisaPasseportId();

    @ManyToOne
    @MapsId("visaId")
    @JoinColumn(name = "id_visa")
    private Visa visa;

    @ManyToOne
    @MapsId("passeportId")
    @JoinColumn(name = "id_passeport")
    private Passeport passeport;

    @Column(name = "status_liaison", length = 50)
    private String statusLiaison;

    public VisaPasseportId getId() { return id; }
    public void setId(VisaPasseportId id) { this.id = id; }
    public Visa getVisa() { return visa; }
    public void setVisa(Visa visa) { this.visa = visa; }
    public Passeport getPasseport() { return passeport; }
    public void setPasseport(Passeport passeport) { this.passeport = passeport; }
    public String getStatusLiaison() { return statusLiaison; }
    public void setStatusLiaison(String statusLiaison) { this.statusLiaison = statusLiaison; }

    @Embeddable
    public static class VisaPasseportId implements Serializable {
        private Long visaId;
        private Long passeportId;

        public VisaPasseportId() {}
        public VisaPasseportId(Long visaId, Long passeportId) {
            this.visaId = visaId;
            this.passeportId = passeportId;
        }

        public Long getVisaId() { return visaId; }
        public void setVisaId(Long visaId) { this.visaId = visaId; }
        public Long getPasseportId() { return passeportId; }
        public void setPasseportId(Long id) { this.passeportId = id; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            VisaPasseportId that = (VisaPasseportId) o;
            return Objects.equals(visaId, that.visaId) && Objects.equals(passeportId, that.passeportId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(visaId, passeportId);
        }
    }
}
