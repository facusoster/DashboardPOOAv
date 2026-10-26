package com.dashboard.dto;

import lombok.Data;

@Data
public class ExecutionResultDTO {

    private String externalId;

    private String status;

    private Integer httpStatus;

    private Integer responseTimeMs;

}
