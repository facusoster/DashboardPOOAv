package com.dashboard.dto;

import lombok.Data;

@Data
public class ApplicationDTO {

    private Long id;

    private String nombre;

    private String url;

    private String descripcion;

    private String tipo;

    private Integer umbralRespuestaMs;

    private Boolean activo;

}