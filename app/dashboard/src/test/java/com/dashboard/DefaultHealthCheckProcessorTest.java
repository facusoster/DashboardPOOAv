package com.dashboard.bridge;

import com.dashboard.dto.IisHealthCheckDTO;
import com.dashboard.dto.ResultDTO;
import com.dashboard.model.ExecutionStatus;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultHealthCheckProcessorTest {

    private final DefaultHealthCheckProcessor processor =
            new DefaultHealthCheckProcessor();

    @Test
    void debeRetornarOKCuandoElResultadoEsValido() {

        ResultDTO result = new ResultDTO();
        result.setStatus("OK");
        result.setResponseTimeMs(500);

        IisHealthCheckDTO dto = new IisHealthCheckDTO();
        dto.setResults(List.of(result));

        ExecutionStatus status =
                processor.calculateFinalStatus(
                        dto,
                        1000);

        assertEquals(
                ExecutionStatus.OK,
                status);
    }

    @Test
    void debeRetornarERRORCuandoExisteUnFAIL() {

        ResultDTO result = new ResultDTO();
        result.setStatus("FAIL");
        result.setResponseTimeMs(100);

        IisHealthCheckDTO dto = new IisHealthCheckDTO();
        dto.setResults(List.of(result));

        ExecutionStatus status =
                processor.calculateFinalStatus(
                        dto,
                        1000);

        assertEquals(
                ExecutionStatus.ERROR,
                status);
    }

    @Test
    void debeRetornarWARNINGCuandoSuperaElUmbral() {

        ResultDTO result = new ResultDTO();
        result.setStatus("OK");
        result.setResponseTimeMs(1001);

        IisHealthCheckDTO dto = new IisHealthCheckDTO();
        dto.setResults(List.of(result));

        ExecutionStatus status =
                processor.calculateFinalStatus(
                        dto,
                        1000);

        assertEquals(
                ExecutionStatus.WARNING,
                status);
    }
}