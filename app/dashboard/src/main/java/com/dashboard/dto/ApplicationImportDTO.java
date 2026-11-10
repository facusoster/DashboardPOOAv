package com.dashboard.dto;

import lombok.Data;

@Data
public class ApplicationImportDTO {

    private String Id;
    private String Name;
    private String Url;
    private String Description;
    private String Criticality;
    private Boolean Enabled;

}