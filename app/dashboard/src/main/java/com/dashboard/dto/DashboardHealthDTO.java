package com.dashboard.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardHealthDTO {

    /**
     * Total de aplicaciones registradas.
     */
    private Integer applicationsCount;

    /**
     * Aplicaciones activas.
     */
    private Integer activeApplications;

    /**
     * Aplicaciones deshabilitadas.
     */
    private Integer inactiveApplications;

    /**
     * Aplicaciones sin datos históricos.
     */
    private Integer unknownApplications;

    /**
     * Score >= 80
     */
    private Integer healthyCount;

    /**
     * Score >= 50 && < 80
     */
    private Integer degradedCount;

    /**
     * Score < 50
     */
    private Integer criticalCount;

    /**
     * Promedio global de salud.
     */
    private Integer averageHealthScore;

    /**
     * Mejor score encontrado.
     */
    private Integer bestHealthScore;

    /**
     * Aplicación con mejor score.
     */
    private String bestApplicationName;

    /**
     * Peor score encontrado.
     */
    private Integer worstHealthScore;

    /**
     * Aplicación con peor score.
     */
    private String worstApplicationName;

    /**
     * Aplicaciones mostradas en el dashboard principal.
     */
    private List<ApplicationHealthDTO> applications;

    /**
     * Rankings de score.
     */
    private List<ApplicationHealthDTO> top5BestScore;

    private List<ApplicationHealthDTO> top5WorstScore;

    /**
     * Rankings de rendimiento basados en la última ejecución conocida.
     */
    private List<ApplicationHealthDTO> top5BestPerformance;

    private List<ApplicationHealthDTO> top5WorstPerformance;
}
