package com.dashboard.service;

import com.dashboard.dto.ApplicationHealthDTO;
import com.dashboard.model.Application;
import com.dashboard.model.ExecutionResult;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationHealthService {

    private final ApplicationService applicationService;

    private final ExecutionResultService executionResultService;

    public ApplicationHealthDTO buildHealth(
            Long applicationId) {

        Application application =
                applicationService.findById(
                        applicationId);

        List<ExecutionResult> results =
                executionResultService
                        .findByApplicationId(
                                applicationId);

        if (results.isEmpty()) {

            return ApplicationHealthDTO.builder()
                    .applicationId(
                            application.getId())
                    .externalId(
                            application.getExternalId())
                    .applicationName(
                            application.getNombre())
                    .criticality(
                            application.getCriticality())
                    .currentStatus(
                            "UNKNOWN")
                    .healthScore(
                            0)
                    .totalExecutions(
                            0)
                    .build();
        }

        ExecutionResult lastResult =
                results.get(0);

        double average =
                executionResultService
                        .calculateAverageResponseTime(
                                applicationId);

        double standardDeviation =
                executionResultService
                        .calculateStandardDeviation(
                                applicationId);

        int minimum =
                executionResultService
                        .calculateMinResponseTime(
                                applicationId);

        int maximum =
                executionResultService
                        .calculateMaxResponseTime(
                                applicationId);

        double dynamicThreshold =
                executionResultService
                        .calculateDynamicThreshold(
                                applicationId);

        /*
         * Score inicial simple.
         */
        int healthScore = 100;

        if ("FAIL".equalsIgnoreCase(
                lastResult.getStatus())) {

            healthScore -= 50;
        }

        if (lastResult.getResponseTimeMs()
                > dynamicThreshold) {

            healthScore -= 20;
        }

        if (lastResult.getResponseTimeMs()
                > application.getUmbralRespuestaMs()) {

            healthScore -= 30;
        }

        if (healthScore < 0) {
            healthScore = 0;
        }

        return ApplicationHealthDTO.builder()
                .applicationId(
                        application.getId())
                .externalId(
                        application.getExternalId())
                .applicationName(
                        application.getNombre())
                .criticality(
                        application.getCriticality())
                .currentStatus(
                        lastResult.getStatus())
                .lastResponseTime(
                        lastResult.getResponseTimeMs())
                .averageResponseTime(
                        average)
                .standardDeviation(
                        standardDeviation)
                .minResponseTime(
                        minimum)
                .maxResponseTime(
                        maximum)
                .dynamicThreshold(
                        dynamicThreshold)
                .totalExecutions(
                        results.size())
                .healthScore(
                        healthScore)
                .build();
    }
}