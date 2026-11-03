package com.dashboard.bridge;

import com.dashboard.dto.IisHealthCheckDTO;
import com.dashboard.model.ExecutionStatus;

public interface HealthCheckProcessor {

    ExecutionStatus calculateFinalStatus(
            IisHealthCheckDTO dto,
            Integer threshold);
}