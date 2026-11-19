package com.dashboard.service;

import com.dashboard.model.ExecutionResult;
import com.dashboard.repository.ExecutionResultRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExecutionResultService {

    private final ExecutionResultRepository repository;

    public ExecutionResult save(
            ExecutionResult executionResult) {

        return repository.save(executionResult);
    }

    public List<ExecutionResult> findByApplicationId(
            Long applicationId) {

        return repository.findByApplicationIdOrderByIdDesc(
                applicationId);
    }

    /**
     * Sprint 1
     * Obtiene únicamente los tiempos de respuesta
     * históricos de una aplicación.
     */
    public List<Integer> getResponseTimes(
            Long applicationId) {

        return repository
                .findByApplicationId(applicationId)
                .stream()
                .map(ExecutionResult::getResponseTimeMs)
                .toList();
    }
}