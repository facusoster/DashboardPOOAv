package com.dashboard.dto;

import lombok.Data;
import java.util.List;

@Data
public class ResultDTO {
    private String Id;
    private String Timestamp;
    private String Name;
    private String Description;
    private String Url;
    private String FinalUrl;
    private Integer RedirectCount;
    private String Criticality;
    private String Status;
    private Integer HttpStatus;
    private List<Integer> ExpectedStatus;
    private Integer TimeoutSeconds;
    private Integer ResponseTimeMs;
    private String Error;
}