
> [!info] 
> **Proyecto:** Dashboard de Monitoreo de Aplicaciones 
> **Materia:** Programación Avanzada (Java + Spring Boot) 
> **Carrera:** Tecnicatura Superior en Análisis de Sistemas 
> **Institución:** Escuela Da Vinci 
> **Profesor:** Lautaro Chiappero 
> **Alumno:** Facundo Soster 
> **Año:** 2026

# 05 · Casos de Uso

Este documento describe los **casos de uso principales** del proyecto _Dashboard de Monitoreo de Aplicaciones_. Los casos de uso representan las interacciones entre los actores del sistema y las funcionalidades implementadas, permitiendo comprender el comportamiento esperado y el alcance funcional del proyecto.

Los casos de uso aquí definidos sirven como base para:

- El diseño de las pantallas Thymeleaf.
- La implementación de controladores, servicios y repositorios.
- Las pruebas unitarias e integración.
- La documentación del parcial y del examen final.

# 1. Actores

## Usuario

Actor que accede al dashboard para visualizar métricas, estados y ejecuciones.

## Administrador

Actor con permisos para administrar aplicaciones monitoreadas, scripts PowerShell y usuarios (en el examen final).

## Sistema

Responsable de procesar health checks, aplicar reglas de negocio y registrar ejecuciones.

## Agente PowerShell

Script externo ejecutado en máquinas Windows que envía resultados de health checks.

# 2. Casos de Uso Principales

A continuación se describen los casos de uso más relevantes del sistema.

# CU01 · Registrar Ejecución de Health Check

**Actor:** Agente PowerShell **Objetivo:** Registrar una ejecución enviada por un script PowerShell y determinar su estado final.

### Flujo Principal

1. El agente PowerShell ejecuta el script asociado a una aplicación monitoreada.
    
2. El script genera un JSON con:
    
    - exitCode
    - responseTimeMs
    - resultado
    - logs
    
3. El agente envía el JSON al endpoint `/healthchecks` mediante HTTP POST.
4. El controlador valida el payload recibido.
5. El servicio aplica la regla de negocio:
    
    - exitCode ≠ 0 → ERROR
    - responseTimeMs > umbral → WARNING
    - resultado.status == "OK" → OK
    
6. Se registra la ejecución en la base de datos.
7. El sistema devuelve una respuesta indicando el estado final.


### Flujo Alternativo

- Si el JSON es inválido → se devuelve error 400 con mensaje claro.
- Si la aplicación no existe → error 404.
- Si ocurre un error interno → error 500 sin exponer trazas.

# CU02 · Consultar Dashboard

**Actor:** Usuario **Objetivo:** Visualizar métricas básicas del sistema.

### Flujo Principal

1. El usuario accede a la pantalla principal del dashboard.
2. El sistema muestra:
    
    - Lista de aplicaciones monitoreadas.
    - Último estado de cada aplicación.
    - Cantidad de ejecuciones.
    - Porcentaje de éxito.
    
3. El usuario selecciona una aplicación para ver más detalles.
4. El sistema muestra las ejecuciones recientes y sus estados.

# CU03 · Consultar Detalle de Ejecución

**Actor:** Usuario **Objetivo:** Ver información detallada de una ejecución específica.

### Flujo Principal

1. El usuario selecciona una ejecución desde el dashboard.
2. El sistema muestra:
    
    - Fecha y hora.
    - exitCode.
    - responseTimeMs.
    - resultadoJson.
    - estadoFinal.
    - logs.


# CU04 · Administrar Aplicaciones Monitoreadas (CRUD)

**Actor:** Administrador **Objetivo:** Gestionar las aplicaciones monitoreadas.

### Flujo Principal

1. El administrador accede a la sección de aplicaciones.
    
2. Puede realizar:
    
    - Alta
    - Modificación
    - Baja lógica o física
    - Listado
    - Consulta de detalle
    
3. El sistema valida:
    
    - Campos obligatorios
    - URL válida
    - Script asociado existente
    
4. El sistema registra los cambios en la base de datos.


### Flujo Alternativo

- Si la aplicación tiene ejecuciones asociadas, la baja puede ser lógica.
- Si los datos son inválidos, se muestran errores de validación.


# CU05 · Administrar Scripts PowerShell (CRUD)

**Actor:** Administrador **Objetivo:** Gestionar los scripts utilizados por los agentes.

### Flujo Principal

1. El administrador accede a la sección de scripts.
    
2. Puede realizar:
    
    - Alta
    - Modificación
    - Baja
    - Listado
    - Consulta de detalle
    
3. El sistema valida:
    
    - Nombre
    - Contenido del script
    - Versionado opcional
    
4. El sistema registra los cambios.

# CU06 · Autenticación y Autorización

**Actor:** Usuario / Administrador **Objetivo:** Acceder al sistema según rol.

### Flujo Principal

1. El usuario ingresa sus credenciales.
    
2. Spring Security valida:
    
    - Usuario existente
    - Contraseña BCrypt
    - Rol asignado
    
3. El sistema permite acceso según rol:
    
    - USER → Dashboard y consultas
    - ADMIN → Dashboard + CRUDs


### Flujo Alternativo

- Credenciales inválidas → mensaje de error sin detalles internos.
- Intento de acceso no autorizado → error 403.

# CU07 · Ver Historial de Ejecuciones

**Actor:** Usuario **Objetivo:** Consultar ejecuciones anteriores de una aplicación.

### Flujo Principal

1. El usuario selecciona una aplicación.
2. El sistema muestra todas las ejecuciones ordenadas por fecha.
3. El usuario puede filtrar por estado (OK, WARNING, ERROR).

# CU08 · Operación de Negocio: Determinar Estado Final

**Actor:** Sistema **Objetivo:** Aplicar la regla de negocio que determina el estado final de una ejecución.

### Flujo Principal

1. El sistema recibe exitCode, responseTimeMs y resultadoJson.
2. Evalúa:
    
    - exitCode
    - tiempo de respuesta
    - estado reportado
    - umbral configurado
    
3. Determina el estado final.
4. Registra el resultado.


Este caso de uso es obligatorio para el parcial y será probado con JUnit.

# 3. Casos de Uso para el Examen Final

Además de los anteriores, el examen final incluye:

- Gestión de usuarios (ADMIN).
- Validación de permisos en servidor.
- Persistencia real en SQLite.
- Ejecutable Windows con Java embebido.

# 4. Diagramas de Casos de Uso

Los diagramas UML se encuentran en:

➡️ [[20_UML_CasosUso]]

# 5. Conclusiones

Los casos de uso definidos permiten comprender el funcionamiento del sistema desde la perspectiva de los actores y establecen una guía clara para la implementación de controladores, servicios, vistas y pruebas.

El caso de uso **CU01** y la operación de negocio **CU08** constituyen el núcleo funcional del parcial, mientras que los CRUDs y la seguridad completan el alcance del examen final.

