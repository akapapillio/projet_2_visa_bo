package com.project.VISA.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.VISA.models.StatusDm;

public interface StatusDmRepository extends JpaRepository<StatusDm, Long> {

    Optional<StatusDm> findByCode(String code);
}
