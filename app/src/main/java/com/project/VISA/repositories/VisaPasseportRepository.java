package com.project.VISA.repositories;

import com.project.VISA.models.VisaPasseport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisaPasseportRepository extends JpaRepository<VisaPasseport, VisaPasseport.VisaPasseportId> {
}
