package com.dashboard.service;

import com.dashboard.model.Application;
import com.dashboard.repository.ApplicationRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository repository;

    public List<Application> findAll() {
        return repository.findAll();
    }

    public Page<Application> findAll(
            Pageable pageable) {

        return repository.findAll(pageable);
    }

    public Page<Application> findFiltered(
            String search,
            Boolean activo,
            Pageable pageable) {

        boolean hasSearch
                = search != null
                && !search.isBlank();

        boolean hasActivo
                = activo != null;

        if (hasSearch && hasActivo) {

            return repository
                    .findByNombreContainingIgnoreCaseAndActivo(
                            search,
                            activo,
                            pageable);
        }

        if (hasSearch) {

            return repository
                    .findByNombreContainingIgnoreCase(
                            search,
                            pageable);
        }

        if (hasActivo) {

            return repository
                    .findByActivo(
                            activo,
                            pageable);
        }

        return repository.findAll(pageable);
    }

    public Application save(
            Application application) {

        return repository.save(application);
    }

    public Application findById(
            Long id) {

        return repository.findById(id)
                .orElseThrow(()
                        -> new RuntimeException(
                                "No se encontró la aplicación con ID: " + id));
    }

    public Application findByExternalId(
            String externalId) {

        return repository.findByExternalId(externalId)
                .orElse(null);
    }

    public void deactivate(
            Long id) {

        Application application
                = findById(id);

        application.setActivo(false);

        repository.save(application);
    }

    public long count() {

        return repository.count();
    }
}
