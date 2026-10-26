package com.dashboard.service;

import com.dashboard.model.Execution;
import com.dashboard.repository.ExecutionRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExecutionService {

    private final ExecutionRepository repository;

    public Execution save(Execution execution) {
        return repository.save(execution);
    }

    public List<Execution> findAll() {
        return repository.findAll();
    }

    public long count() {
        return repository.count();
    }
}