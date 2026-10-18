package com.dashboard.dto;

import lombok.Data;

@Data
public class SummaryDTO {
    private String Status;
    private Integer Total;
    private Integer OK;
    private Integer FAIL;
}