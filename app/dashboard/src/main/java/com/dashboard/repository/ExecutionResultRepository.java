package com.dashboard.repository;

import com.dashboard.model.ExecutionResult;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExecutionResultRepository
        extends JpaRepository<ExecutionResult, Long> {

    List<ExecutionResult> findByApplicationIdOrderByIdDesc(
            Long applicationId);
}