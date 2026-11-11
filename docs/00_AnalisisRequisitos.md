

> [!info] 
> **Proyecto:** Dashboard de Monitoreo de Aplicaciones  
> **Materia:** Programación Avanzada (Java + Spring Boot)  
> **Carrera:** Tecnicatura Superior en Análisis de Sistemas  
> **Institución:** Escuela Da Vinci  
> **Profesor:** Lautaro Chiappero  
> **Alumno:** Facundo Soster  
> **Año:** 2026  

# Introducción

Este documento presenta el relevamiento de requisitos realizado durante la etapa de análisis del proyecto **Dashboard de Monitoreo de Aplicaciones**, una aplicación web desarrollada con **Java 21**, **Spring Boot 3**, **Thymeleaf** y **SQLite** como base de datos embebida.

El sistema centraliza resultados de _health checks_ enviados desde máquinas Windows mediante scripts PowerShell, procesándolos según reglas de negocio y mostrando métricas básicas en un dashboard.

Los requisitos aquí definidos establecen el alcance académico del proyecto y sirven como base para el diseño del modelo de dominio, la arquitectura en capas y la implementación del patrón GoF **Bridge**.

# Objetivo General

Desarrollar una aplicación web que permita monitorear aplicaciones ejecutadas en máquinas Windows mediante health checks enviados por scripts PowerShell, procesando resultados, registrando ejecuciones y mostrando métricas básicas en un dashboard accesible por usuarios con distintos roles.

# Objetivos Específicos

- Recibir resultados de health checks enviados por scripts PowerShell.
- Procesar los resultados aplicando una regla de negocio que determine el estado final.
- Registrar ejecuciones y logs asociados.
- Mostrar métricas básicas de disponibilidad y estado general.
- Implementar un CRUD de aplicaciones monitoreadas.
- Implementar un CRUD de scripts PowerShell almacenados en la base de datos.
- Incorporar autenticación y autorización con roles (Administrador / Usuario).
- Aplicar el patrón GoF **Bridge** para desacoplar la definición de aplicaciones y scripts del procesamiento interno.
- Documentar todas las etapas del proyecto en formato Markdown.

# Actores del Sistema

## Usuario

Corresponde al usuario que accede al dashboard para visualizar métricas y estados de las aplicaciones monitoreadas.

Entre sus principales acciones se encuentran:

- Iniciar sesión.
- Consultar el dashboard.
- Ver el estado de las aplicaciones monitoreadas.
- Consultar ejecuciones y logs.

## Administrador

Es el usuario responsable de la administración del sistema.

Posee permisos para:

- Gestionar aplicaciones monitoreadas (CRUD).
- Gestionar scripts PowerShell (CRUD).
- Consultar todas las ejecuciones y logs.
- Configurar parámetros del sistema.
- Administrar usuarios (para el examen final).

# Requisitos Funcionales

|ID|Requisito|Actor|
|---|---|---|
|RF01|El sistema debe recibir resultados de health checks enviados por scripts PowerShell.|Sistema|
|RF02|El sistema debe validar el payload recibido.|Sistema|
|RF03|El sistema debe aplicar una regla de negocio para determinar el estado final de la ejecución.|Sistema|
|RF04|El sistema debe registrar ejecuciones y logs asociados.|Sistema|
|RF05|El usuario debe poder consultar el dashboard con métricas básicas.|Usuario|
|RF06|El usuario debe poder consultar el detalle de una ejecución.|Usuario|
|RF07|El administrador debe poder crear aplicaciones monitoreadas.|Administrador|
|RF08|El administrador debe poder modificar aplicaciones monitoreadas.|Administrador|
|RF09|El administrador debe poder eliminar aplicaciones monitoreadas.|Administrador|
|RF10|El administrador debe poder crear scripts PowerShell.|Administrador|
|RF11|El administrador debe poder modificar scripts PowerShell.|Administrador|
|RF12|El administrador debe poder eliminar scripts PowerShell.|Administrador|
|RF13|El sistema debe restringir el acceso a funcionalidades según el rol del usuario.|Sistema|
|RF14|El sistema debe permitir iniciar y cerrar sesión.|Usuario|
|RF15|El sistema debe mostrar métricas básicas (estado, cantidad de ejecuciones, porcentaje de éxito).|Sistema|

# Requisitos No Funcionales

|ID|Requisito|
|---|---|
|RNF01|La aplicación debe desarrollarse utilizando Java 21 y Spring Boot 3.|
|RNF02|La persistencia debe implementarse mediante SQLite embebido.|
|RNF03|La arquitectura debe respetar la separación en capas: Controller, Service, Repository/DAO, DTO y Vista.|
|RNF04|Las contraseñas deben almacenarse utilizando BCrypt.|
|RNF05|La interfaz debe implementarse con Thymeleaf, HTML y CSS.|
|RNF06|El proyecto debe estar completamente documentado en formato Markdown.|
|RNF07|El código fuente debe mantenerse bajo control de versiones mediante Git.|
|RNF08|El sistema debe ser portable y ejecutarse en Windows sin instalar Java.|
|RNF09|El patrón GoF **Bridge** debe aplicarse en código propio y justificar su uso.|
|RNF10|El sistema debe validar entradas y manejar errores sin exponer trazas internas.|

# Casos de Uso Principales

Los procesos funcionales más relevantes identificados durante el análisis son:

- Registrar ejecución de health check.
- Procesar resultado y determinar estado final.
- Consultar dashboard.
- Consultar detalle de ejecución.
- Gestionar aplicaciones monitoreadas (CRUD).
- Gestionar scripts PowerShell (CRUD).
- Autenticación y autorización.

La descripción detallada de estos procesos puede consultarse en:

➡️ [[05_CasosUso]]

# Alcance del Proyecto

El proyecto contempla la implementación de un sistema de monitoreo básico que permita administrar aplicaciones monitoreadas, scripts PowerShell y ejecuciones mediante una aplicación web desarrollada con Java y Spring Boot.

Incluye:

- Recepción y procesamiento de health checks.
- Registro de ejecuciones y logs.
- Dashboard con métricas básicas.
- CRUD de aplicaciones monitoreadas.
- CRUD de scripts PowerShell.
- Autenticación y roles.
- Documentación técnica completa.
- Ejecutable Windows con Java embebido (para el examen final).

# Fuera del Alcance

Con el objetivo de mantener el proyecto dentro del alcance académico definido por la asignatura, no se implementan las siguientes funcionalidades:

- Métricas históricas avanzadas.
- Alertas por correo, Slack u otros canales.
- Monitoreo distribuido entre múltiples agentes.
- Dashboards gráficos complejos (charts dinámicos).
- API REST pública completa.
- Integración con sistemas externos de monitoreo.
- Notificaciones push.
- Gestión avanzada de logs.

# Supuestos

Durante el desarrollo se asumieron las siguientes condiciones:

- Los scripts PowerShell se ejecutan correctamente en las máquinas Windows.
- Los agentes envían datos válidos y en el formato esperado.
- El sistema se ejecuta en un entorno local o controlado.
- Los administradores poseen permisos globales sobre el sistema.
- Los usuarios solo pueden consultar información, no modificarla.
- La base de datos SQLite es suficiente para el alcance académico.

# Restricciones

El desarrollo se realizó respetando los lineamientos establecidos por la materia:

- Uso obligatorio de Java 17+ y Spring Boot 3.
- Arquitectura MVC + Service + Repository + DAO + DTO.
- Base de datos SQLite embebida.
- Patrón GoF **Bridge** aplicado en código propio.
- Validaciones y reglas de negocio implementadas en servicios.
- Documentación técnica del proyecto en Markdown.

# Trazabilidad

El presente análisis constituye el punto de partida para el resto de la documentación técnica.

Los requisitos aquí definidos se reflejan posteriormente en:

- [[Proyecto Laravel/docs/01_Arquitectura]]
- [[Proyecto Laravel/docs/02_ModeloDominio]]
- [[Proyecto Laravel/docs/03_BaseDatos]]
- [[Proyecto Laravel/docs/04_DER]]
- [[05_CasosUso]]
- [[Proyecto Laravel/docs/07_UML]]
- [[Proyecto Laravel/docs/08_ManualTecnico]]

# Conclusiones

El relevamiento permitió definir el alcance funcional del sistema y establecer una base sólida para el diseño e implementación del **Dashboard de Monitoreo de Aplicaciones**.

La separación entre requisitos funcionales, no funcionales, actores y restricciones facilita el desarrollo incremental del proyecto, manteniendo coherencia entre el análisis inicial, el modelo de datos, la arquitectura en capas y el código implementado en Java y Spring Boot.
