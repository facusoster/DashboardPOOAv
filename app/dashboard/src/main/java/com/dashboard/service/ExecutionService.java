package com.dashboard.service;

import com.dashboard.model.Execution;
import com.dashboard.model.ExecutionStatus;
import com.dashboard.repository.ExecutionRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExecutionService {

    private final ExecutionRepository repository;

    public Execution save(
            Execution execution) {

        return repository.save(execution);
    }

    /**
     * Método actual.
     */
    public List<Execution> findAll() {

        return repository.findAll();
    }

    /**
     * Nuevo método para paginación.
     */
    public Page<Execution> findFiltered(
            ExecutionStatus status,
            Pageable pageable) {

        if (status != null) {

            return repository.findByStatus(
                    status,
                    pageable);
        }

        return repository.findAll(pageable);
    }

    public long count() {

        return repository.count();
    }

    public long countOk() {

        return repository.countByStatus(
                ExecutionStatus.OK);
    }

    public long countDegraded() {

        return repository.countByStatus(
                ExecutionStatus.DEGRADED);
    }

    public long countWarning() {

        return repository.countByStatus(
                ExecutionStatus.WARNING);
    }

    public long countError() {

        return repository.countByStatus(
                ExecutionStatus.ERROR);
    }
}
