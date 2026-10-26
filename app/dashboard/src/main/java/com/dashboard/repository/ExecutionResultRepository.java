package com.dashboard.repository;

import com.dashboard.model.ExecutionResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExecutionResultRepository
        extends JpaRepository<ExecutionResult, Long> {

}