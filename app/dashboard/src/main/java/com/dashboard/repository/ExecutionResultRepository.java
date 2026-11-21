package com.dashboard.repository;

import com.dashboard.model.ExecutionResult;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExecutionResultRepository
        extends JpaRepository<ExecutionResult, Long> {

    List<ExecutionResult>
            findByApplicationIdOrderByIdDesc(
                    Long applicationId);

    List<ExecutionResult>
            findByApplicationId(
                    Long applicationId);

    /**
     * Último resultado conocido para una aplicación.
     */
    ExecutionResult
            findFirstByApplicationIdOrderByIdDesc(
                    Long applicationId);

    /**
     * Últimos 50 resultados para score y tendencias.
     */
    List<ExecutionResult>
            findTop50ByApplicationIdOrderByIdDesc(
                    Long applicationId);

    long countByApplicationId(
            Long applicationId);

    /**
     * Rendimiento.
     */
    List<ExecutionResult>
            findTop5ByOrderByResponseTimeMsAsc();

    List<ExecutionResult>
            findTop5ByOrderByResponseTimeMsDesc();

    /**
     * Confiabilidad.
     */
    List<ExecutionResult>
            findTop5ByStatusOrderByResponseTimeMsAsc(
                    String status);
}
