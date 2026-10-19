package com.dashboard.dto;

import lombok.Data;

@Data
public class ExecutionDTO {
    private String StartTime;
    private String EndTime;
    private Integer DurationMs;
}