package com.dashboard.model;

import jakarta.persistence.*;

@Entity
@Table(name = "execution_results")
public class ExecutionResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    public ExecutionResult() {
    }

}