package com.dashboard.repository;

import com.dashboard.model.Application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository
        extends JpaRepository<Application, Long> {

    Optional<Application> findByExternalId(
            String externalId);

    Page<Application> findByNombreContainingIgnoreCase(
            String nombre,
            Pageable pageable);

    Page<Application> findByActivo(
            Boolean activo,
            Pageable pageable);

    Page<Application>
            findByNombreContainingIgnoreCaseAndActivo(
                    String nombre,
                    Boolean activo,
                    Pageable pageable);
}
