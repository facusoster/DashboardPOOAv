package com.dashboard.service;

import com.dashboard.bridge.HealthCheckProcessor;
import com.dashboard.dto.IisHealthCheckDTO;
import com.dashboard.model.Execution;
import com.dashboard.model.ExecutionStatus;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class HealthCheckService {

    private final ExecutionService executionService;

    private final HealthCheckProcessor processor;

        public void process(IisHealthCheckDTO dto) {

        ExecutionStatus finalStatus =
                processor.calculateFinalStatus(
                        dto,
                        1000);

        Execution execution = Execution.builder()
                .executionId(dto.getExecutionId())
                .component(dto.getComponent())
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

            executionService.save(execution);

            System.out.println(
            "Execution guardada: "
                            + dto.getExecutionId());

            System.out.println(
                    "Estado final calculado: "
                            + finalStatus);
        }
}