package com.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationHealthDTO {

    /**
     * Identificación
     */
    private Long applicationId;

    private String externalId;

    private String applicationName;

    private String criticality;

    /**
     * Estado actual
     */
    private String currentStatus;

    /**
     * Última ejecución registrada
     */
    private LocalDateTime lastExecutionDate;

    /**
     * Última respuesta observada
     */
    private Integer lastResponseTime;

    /**
     * Métricas históricas
     */
    private Double averageResponseTime;

    private Double standardDeviation;

    private Integer minResponseTime;

    private Integer maxResponseTime;

    private Double dynamicThreshold;

    /**
     * Volumen de datos utilizado
     */
    private Integer totalExecutions;

    /**
     * Score entre 0 y 100
     */
    private Integer healthScore;
}
