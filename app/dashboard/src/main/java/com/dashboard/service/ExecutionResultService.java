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

    public List<Integer> getResponseTimes(
            Long applicationId) {

        return repository
                .findByApplicationId(applicationId)
                .stream()
                .map(ExecutionResult::getResponseTimeMs)
                .toList();
    }

    public double calculateAverageResponseTime(
            Long applicationId) {

        List<Integer> responseTimes =
                getResponseTimes(applicationId);

        if (responseTimes.isEmpty()) {
            return 0;
        }

        return responseTimes
                .stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0);
    }

    /**
     * Calcula el desvío estándar poblacional de los tiempos de respuesta de una aplicación.
     * Desvío estándar poblacional.
     */
    public double calculateStandardDeviation(
            Long applicationId) {

        List<Integer> responseTimes =
                getResponseTimes(applicationId);

        if (responseTimes.size() <= 1) {
            return 0;
        }

        double average =
                calculateAverageResponseTime(
                        applicationId);

        double variance =
                responseTimes
                        .stream()
                        .mapToDouble(value ->
                                Math.pow(
                                        value - average,
                                        2))
                        .average()
                        .orElse(0);

        return Math.sqrt(variance);
    }
}