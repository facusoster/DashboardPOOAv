package com.dashboard.bridge;

import com.dashboard.dto.IisHealthCheckDTO;
import com.dashboard.dto.ResultDTO;
import com.dashboard.model.ExecutionStatus;

import org.springframework.stereotype.Component;

@Component
public class DefaultHealthCheckProcessor
        implements HealthCheckProcessor {

    @Override
    public ExecutionStatus calculateFinalStatus(
            IisHealthCheckDTO dto,
            Integer threshold,
            Double dynamicThreshold) {

        for (ResultDTO result : dto.getResults()) {

            if ("FAIL".equalsIgnoreCase(
                    result.getStatus())) {

                return ExecutionStatus.ERROR;
            }

            /*
             * Umbral fijo definido
             * por el administrador.
             *
             * Situación crítica.
             */
            if (result.getResponseTimeMs()
                    > threshold) {

                return ExecutionStatus.WARNING;
            }

            /*
             * Baseline adaptativo.
             *
             * La aplicación sigue funcionando,
             * pero se comporta peor que su
             * histórico normal.
             */
            if (result.getResponseTimeMs()
                    > dynamicThreshold) {

                return ExecutionStatus.DEGRADED;
            }
        }

        return ExecutionStatus.OK;
    }
}