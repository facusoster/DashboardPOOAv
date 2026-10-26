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

    public long count() {
        return repository.count();
    }
}