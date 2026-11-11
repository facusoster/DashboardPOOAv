package com.dashboard.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "execution_results")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExecutionResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String externalId;

    @Column(nullable = false)
    private String applicationName;

    @Column(nullable = false)
    private String status;

    private Integer httpStatus;

    @Column(nullable = false)
    private Integer responseTimeMs;

    private String error;

    @ManyToOne
    @JoinColumn(name = "execution_id", nullable = false)
    private Execution execution;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;
}