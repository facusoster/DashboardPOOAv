package com.dashboard.model;

import jakarta.persistence.*;

@Entity
@Table(name = "scripts")
public class Script {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String descripcion;

    private String version;

    @Lob
    private String contenido;

    public Script() {
    }

}