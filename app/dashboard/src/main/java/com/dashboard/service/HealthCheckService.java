package com.dashboard.service;

import com.dashboard.bridge.HealthCheckProcessor;
import com.dashboard.dto.IisHealthCheckDTO;
import com.dashboard.dto.ResultDTO;
import com.dashboard.model.Application;
import com.dashboard.model.Execution;
import com.dashboard.model.ExecutionResult;
import com.dashboard.model.ExecutionStatus;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class HealthCheckService {

    private final ExecutionService executionService;

    private final ExecutionResultService executionResultService;

    private final ApplicationService applicationService;

    private final HealthCheckProcessor processor;

    public void process(IisHealthCheckDTO dto) {

        Application firstApplication = null;

        Integer threshold = 1000;

        if (dto.getResults() != null
                && !dto.getResults().isEmpty()) {

            String externalId =
                    dto.getResults()
                            .get(0)
                            .getId();

            firstApplication =
                    applicationService
                            .findByExternalId(
                                    externalId);

            if (firstApplication != null
                    && firstApplication.getUmbralRespuestaMs() != null) {

                threshold =
                        firstApplication.getUmbralRespuestaMs();
            }
        }

        ExecutionStatus finalStatus =
                processor.calculateFinalStatus(
                        dto,
                        threshold);

        Execution execution = Execution.builder()
                .executionId(dto.getExecutionId())
                .component(dto.getComponent())
                .application(firstApplication)
                .startTime(
                        LocalDateTime.parse(
                                dto.getExecution()
                                        .getStartTime()
                                        .replace(" ", "T")))
                .endTime(
                        LocalDateTime.parse(
                                dto.getExecution()
                                        .getEndTime()
                                        .replace(" ", "T")))
                .durationMs(
                        dto.getExecution()
                                .getDurationMs())
                .status(finalStatus)
                .total(
                        dto.getSummary()
                                .getTotal())
                .okCount(
                        dto.getSummary()
                                .getOK())
                .failCount(
                        dto.getSummary()
                                .getFAIL())
                .build();

        execution = executionService.save(execution);

        for (ResultDTO result : dto.getResults()) {

            Application application =
                    applicationService.findByExternalId(
                            result.getId());

            ExecutionResult executionResult =
                    ExecutionResult.builder()
                            .externalId(result.getId())
                            .applicationName(result.getName())
                            .status(result.getStatus())
                            .httpStatus(result.getHttpStatus())
                            .responseTimeMs(
                                    result.getResponseTimeMs())
                            .error(result.getError())
                            .execution(execution)
                            .application(application)
                            .build();

            executionResultService.save(
                    executionResult);
        }

        System.out.println(
                "Execution guardada: "
                        + dto.getExecutionId());

        System.out.println(
                "Estado final calculado: "
                        + finalStatus);
    }
}