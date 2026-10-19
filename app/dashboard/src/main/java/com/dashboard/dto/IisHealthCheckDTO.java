package com.dashboard.dto;

import lombok.Data;
import java.util.List;

@Data
public class IisHealthCheckDTO {
    private String Application;
    private String Component;
    private String ExecutionId;
    private ExecutionDTO Execution;
    private SummaryDTO Summary;
    private List<ResultDTO> Results;
}
