package com.dashboard.repository;

import com.dashboard.model.Execution;
import com.dashboard.model.ExecutionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExecutionRepository
        extends JpaRepository<Execution, Long> {

    long countByStatus(ExecutionStatus status);

}