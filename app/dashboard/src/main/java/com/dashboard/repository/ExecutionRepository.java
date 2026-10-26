package com.dashboard.repository;

import com.dashboard.model.Execution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExecutionRepository
        extends JpaRepository<Execution, Long> {

}