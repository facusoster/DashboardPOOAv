
> [!info] 
> **Proyecto:** Dashboard de Monitoreo de Aplicaciones 
> **Materia:** Programación Avanzada (Java + Spring Boot) 
> **Carrera:** Tecnicatura Superior en Análisis de Sistemas 
> **Institución:** Escuela Da Vinci 
> **Profesor:** Lautaro Chiappero 
> **Alumno:** Facundo Soster 
> **Año:** 2026

# 04 · Diagrama Entidad–Relación (DER)

Este documento presenta el **Diagrama Entidad–Relación (DER)** del proyecto _Dashboard de Monitoreo de Aplicaciones_. El DER modela la estructura de datos persistentes del sistema y sirve como base para la implementación de las entidades JPA, la configuración de la base de datos SQLite y la definición de migraciones o scripts SQL.

El modelo se centra en tres entidades principales del dominio:

- **AplicaciónMonitoreada**
- **ScriptPowerShell**
- **EjecuciónHealthCheck**

Además, se incluye la entidad **Usuario**, necesaria para la autenticación y autorización del sistema.

# 1. Entidades y Atributos

A continuación se detallan las entidades del sistema y sus atributos principales.

## 1.1 AplicacionMonitoreada

|Campo|Tipo|PK|FK|Nulo|Descripción|
|---|---|---|---|---|---|
|id|INTEGER|✔|✘|✘|Identificador único.|
|nombre|TEXT|✘|✘|✘|Nombre de la aplicación monitoreada.|
|url|TEXT|✘|✘|✘|URL o endpoint a verificar.|
|descripcion|TEXT|✘|✘|✔|Descripción opcional.|
|tipo|TEXT|✘|✘|✘|Tipo de aplicación (IIS, Tomcat, Custom).|
|umbral_respuesta_ms|INTEGER|✘|✘|✘|Tiempo máximo aceptado para considerar OK.|
|activo|BOOLEAN|✘|✘|✘|Indica si la aplicación está habilitada.|
|script_id|INTEGER|✘|✔|✘|Relación con ScriptPowerShell.|

## 1.2 ScriptPowerShell

|Campo|Tipo|PK|FK|Nulo|Descripción|
|---|---|---|---|---|---|
|id|INTEGER|✔|✘|✘|Identificador único.|
|nombre|TEXT|✘|✘|✘|Nombre del script.|
|contenido|TEXT|✘|✘|✘|Código PowerShell almacenado.|
|descripcion|TEXT|✘|✘|✔|Descripción del script.|
|version|TEXT|✘|✘|✔|Versión del script.|

## 1.3 EjecucionHealthCheck

|Campo|Tipo|PK|FK|Nulo|Descripción|
|---|---|---|---|---|---|
|id|INTEGER|✔|✘|✘|Identificador único.|
|fecha_hora|TEXT|✘|✘|✘|Fecha y hora de la ejecución (ISO).|
|exit_code|INTEGER|✘|✘|✘|Código de salida del script.|
|response_time_ms|INTEGER|✘|✘|✘|Tiempo de respuesta medido.|
|resultado_json|TEXT|✘|✘|✘|Resultado bruto enviado por el agente.|
|estado_final|TEXT|✘|✘|✘|Estado calculado (OK, WARNING, ERROR).|
|logs|TEXT|✘|✘|✔|Logs o mensajes relevantes.|
|aplicacion_id|INTEGER|✘|✔|✘|Relación con AplicacionMonitoreada.|
|script_id|INTEGER|✘|✔|✘|Relación con ScriptPowerShell.|

## 1.4 Usuario

|Campo|Tipo|PK|FK|Nulo|Descripción|
|---|---|---|---|---|---|
|id|INTEGER|✔|✘|✘|Identificador único.|
|username|TEXT|✘|✘|✘|Nombre de usuario.|
|password|TEXT|✘|✘|✘|Contraseña encriptada con BCrypt.|
|rol|TEXT|✘|✘|✘|ADMIN o USER.|

# 2. Relaciones del Modelo

A continuación se describen las relaciones entre las entidades del sistema.

## 2.1 AplicacionMonitoreada — ScriptPowerShell

**Tipo:** 1 a 1 **Descripción:** Cada aplicación monitoreada utiliza un único script PowerShell para ejecutar su health check.

Código

```
AplicacionMonitoreada.script_id → ScriptPowerShell.id
```

## 2.2 AplicacionMonitoreada — EjecucionHealthCheck

**Tipo:** 1 a N **Descripción:** Una aplicación puede tener múltiples ejecuciones registradas.

Código

```
EjecucionHealthCheck.aplicacion_id → AplicacionMonitoreada.id
```

## 2.3 ScriptPowerShell — EjecucionHealthCheck

**Tipo:** 1 a N **Descripción:** Un script puede ser utilizado en múltiples ejecuciones.

Código

```
EjecucionHealthCheck.script_id → ScriptPowerShell.id
```

## 2.4 Usuario — (Aplicaciones / Scripts / Ejecuciones)

**Tipo:** No aplica en el DER **Descripción:** La entidad Usuario no se relaciona directamente con las entidades del dominio, pero controla el acceso mediante roles en la capa de seguridad.

# 3. DER (Representación Conceptual)

Representación textual del diagrama:

Código

```
+-----------------------+        +-----------------------+
| AplicacionMonitoreada |        |   ScriptPowerShell    |
+-----------------------+        +-----------------------+
| id (PK)               | 1    1 | id (PK)               |
| nombre                |--------| nombre                |
| url                   |        | contenido             |
| tipo                  |        | descripcion           |
| umbral_respuesta_ms   |        | version               |
| activo                |        +-----------------------+
| script_id (FK)        |
+-----------------------+
            |
            | 1
            | 
            | N
+-----------------------+
| EjecucionHealthCheck  |
+-----------------------+
| id (PK)               |
| fecha_hora            |
| exit_code             |
| response_time_ms      |
| resultado_json        |
| estado_final          |
| logs                  |
| aplicacion_id (FK)    |
| script_id (FK)        |
+-----------------------+
```

# 4. Restricciones y Reglas de Integridad

- **Integridad referencial:**
    
    - `script_id` en AplicacionMonitoreada debe existir en ScriptPowerShell.
    - `aplicacion_id` y `script_id` en EjecucionHealthCheck deben existir en sus respectivas tablas.
    
- **Restricciones de negocio:**
    
    - Una aplicación debe tener un script asignado.
    - Una ejecución debe pertenecer a una aplicación y a un script.
    - El estado final debe ser uno de: `OK`, `WARNING`, `ERROR`.
    
- **Restricciones técnicas:**
    
    - SQLite no soporta todos los tipos avanzados de SQL, por lo que se utilizan tipos TEXT/INTEGER.
    - Las fechas se almacenan como TEXT en formato ISO-8601.
    

# 5. Conclusiones

El DER definido proporciona una estructura clara, coherente y alineada con el modelo de dominio y las necesidades del sistema. Sirve como base para:

- La implementación de entidades JPA.
- La generación de scripts SQL para SQLite.
- La documentación del modelo de datos.
- La validación del diseño durante el parcial y el examen final.
