# Dashboard de Monitoreo de Aplicaciones

Proyecto académico desarrollado para la materia **Programación Avanzada** de la carrera **Tecnicatura Superior en Análisis de Sistemas** (Escuela Da Vinci).

## Autor

- Facundo Soster

## Profesor

- Lautaro Chiappero

---

# Descripción

Dashboard web desarrollado con Java 21 y Spring Boot para centralizar resultados de health checks ejecutados mediante scripts PowerShell en servidores Windows.

El sistema recibe resultados en formato JSON mediante una API REST, procesa la información aplicando reglas de negocio y registra las ejecuciones para su visualización posterior.

---

# Tecnologías

## Backend

- Java 21
- Spring Boot 4.1.1
- Spring Web
- Spring Data JPA
- Lombok

## Base de datos

### Desarrollo

- PostgreSQL 16 (Docker)

### Producción

- SQLite (pendiente)

## Infraestructura

- Docker
- Docker Compose

## Agentes

- PowerShell
- Task Scheduler (Windows)

---

# Arquitectura

```text
PowerShell
    │
    ▼
API REST
(Spring Boot)
    │
    ▼
Service Layer
    │
    ▼
Repository
    │
    ▼
PostgreSQL
```

---

# Estructura del Proyecto

```text
dashboard
│
├── app
│   └── dashboard
│       ├── src
│       │   ├── main
│       │   │   ├── java
│       │   │   └── resources
│       │   └── test
│
├── docker-compose.yml
├── logs
└── README.md
```

---

# Entorno de Desarrollo

## Servidor Linux

```text
Hostname : ubuntu-01
Usuario  : facusoster

```

---

## PostgreSQL

Docker Compose:

```yaml
postgres:
  image: postgres:16
```


---

# Scripts PowerShell

Ubicación:

```text
D:\TestWeb\Scripts
```

Scripts:

```text
HealthCheck-IIS.ps1
HealthCheck-App.ps1
HealthCheck-Tomcat.ps1
```

---

# Logs

Ubicación:

```text
D:\TestWeb\Logs
```

Estructura:

```text
Logs
├── IIS
├── App
└── Tomcat
```

Los scripts generan archivos JSON que posteriormente serán enviados a la API REST.

---

# API

Estado actual:

- [x] Proyecto Spring Boot generado
- [x] PostgreSQL configurado
- [x] DTOs IIS implementados
- [x] Controllers API implementados
- [x] Perfil DEV configurado

Pendiente:

- [ ] Entidades JPA
- [ ] Repositories
- [ ] Persistencia PostgreSQL
- [ ] Dashboard Thymeleaf
- [ ] Seguridad
- [ ] Patrón Bridge
- [ ] SQLite
- [ ] Ejecutable Windows

---

# Documentación

La documentación funcional y técnica se encuentra dentro de la carpeta Wiki del proyecto.

Documentos principales:

- 00_AnalisisRequisitos.md
- 01_Arquitectura.md
- 02_ModeloDominio.md
- 03_BaseDatos.md
- 04_DER.md
- 05_CasosUso.md
- 07_UML.md

---

# Estado Actual

Versión inicial del backend.

Actualmente la API recibe health checks IIS mediante DTOs y se encuentra en desarrollo la capa de persistencia utilizando PostgreSQL.