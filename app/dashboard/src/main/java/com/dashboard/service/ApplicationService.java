package com.dashboard.service;

import com.dashboard.model.Application;
import com.dashboard.repository.ApplicationRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository repository;

    public List<Application> findAll() {
        return repository.findAll();
    }

    public Application save(Application application) {
        return repository.save(application);
    }

    public Application findById(Long id) {
        return repository.findById(id)
                .orElseThrow(()
                        -> new RuntimeException(
                                "No se encontró la aplicación con ID: " + id));
    }

    public void deactivate(Long id) {
        Application application = findById(id);
        application.setActivo(false);
        repository.save(application);
    }

    public long count() {
        return repository.count();
    }
}
