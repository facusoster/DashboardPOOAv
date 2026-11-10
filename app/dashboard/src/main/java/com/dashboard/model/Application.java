package com.dashboard.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String externalId;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String url;

    private String descripcion;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private String criticality;

    @Column(nullable = false)
    private Integer umbralRespuestaMs;

    @Column(nullable = false)
    private Boolean activo;

}