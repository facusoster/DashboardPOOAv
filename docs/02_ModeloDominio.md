
> [!info] 
> **Proyecto:** Dashboard de Monitoreo de Aplicaciones 
> **Materia:** Programación Avanzada (Java + Spring Boot) 
> **Carrera:** Tecnicatura Superior en Análisis de Sistemas 
> **Institución:** Escuela Da Vinci 
> **Profesor:** Lautaro Chiappero 
> **Alumno:** Facundo Soster 
> **Año:** 2026

# 02 · Modelo de Dominio

Este documento describe el **modelo de dominio** del proyecto _Dashboard de Monitoreo de Aplicaciones_, detallando las entidades principales, sus atributos, relaciones y restricciones. El modelo constituye la base conceptual sobre la cual se construyen el DER, las entidades JPA, los DTO y la lógica de negocio implementada en los servicios.

El dominio se centra en la recepción, procesamiento y registro de resultados de health checks enviados desde máquinas Windows mediante scripts PowerShell.

# 1. Visión General del Dominio

El sistema gestiona tres entidades principales:

1. **AplicaciónMonitoreada**
2. **ScriptPowerShell**
3. **EjecuciónHealthCheck**

Estas entidades representan:

- Las aplicaciones que se monitorean.
- Los scripts que se utilizan para realizar los health checks.
- Las ejecuciones registradas con sus resultados y estado final.

Además, el sistema incorpora usuarios y roles para autenticación y autorización, aunque no forman parte del mínimo de tres entidades de dominio exigidas por la consigna.

# 2. Entidades del Dominio

## 2.1 AplicaciónMonitoreada

Representa una aplicación o servicio que será monitoreado mediante health checks.

**Atributos principales:**

|Atributo|Tipo|Descripción|
|---|---|---|
|id|Long|Identificador único.|
|nombre|String|Nombre de la aplicación monitoreada.|
|url|String|URL o endpoint a verificar.|
|descripcion|String|Descripción opcional.|
|tipo|String|Tipo de aplicación (IIS, Tomcat, Custom, etc.).|
|umbralRespuestaMs|Integer|Tiempo máximo aceptado para considerar la ejecución como OK.|
|activo|Boolean|Indica si la aplicación está habilitada para monitoreo.|

**Relaciones:**

- **1..*** con `EjecuciónHealthCheck`
- **1..1** con `ScriptPowerShell` (asociación configurable)

## 2.2 ScriptPowerShell

Representa un script almacenado en la base de datos que se utiliza para ejecutar health checks en máquinas Windows.

**Atributos principales:**

|Atributo|Tipo|Descripción|
|---|---|---|
|id|Long|Identificador único.|
|nombre|String|Nombre del script.|
|contenido|String|Código PowerShell almacenado.|
|descripcion|String|Descripción del propósito del script.|
|version|String|Versión del script.|

**Relaciones:**

- **1..*** con `AplicaciónMonitoreada` (una aplicación utiliza un script)
- **1..*** con `EjecuciónHealthCheck` (cada ejecución referencia el script utilizado)

## 2.3 EjecuciónHealthCheck

Representa una ejecución concreta de un health check enviada por un agente PowerShell.

**Atributos principales:**

|Atributo|Tipo|Descripción|
|---|---|---|
|id|Long|Identificador único.|
|fechaHora|LocalDateTime|Fecha y hora de la ejecución.|
|exitCode|Integer|Código de salida del script.|
|responseTimeMs|Integer|Tiempo de respuesta medido.|
|resultadoJson|String|Resultado bruto enviado por el agente.|
|estadoFinal|String|Estado calculado por la regla de negocio (OK, WARNING, ERROR).|
|logs|String|Logs o mensajes relevantes.|

**Relaciones:**

- **1..1** con `AplicaciónMonitoreada`
- **1..1** con `ScriptPowerShell`

## 2.4 Usuario

Entidad utilizada para autenticación y autorización.

**Atributos principales:**

|Atributo|Tipo|Descripción|
|---|---|---|
|id|Long|Identificador único.|
|username|String|Nombre de usuario.|
|password|String|Contraseña encriptada con BCrypt.|
|rol|Enum|ADMIN o USER.|

**Relaciones:**

- No participa directamente en el dominio de monitoreo, pero controla acceso a CRUDs y dashboard.

# 3. Relaciones del Modelo

A continuación se describen las relaciones entre las entidades principales:

### **AplicaciónMonitoreada — EjecuciónHealthCheck**

- **1..*** Una aplicación puede tener múltiples ejecuciones registradas.

### **ScriptPowerShell — EjecuciónHealthCheck**

- **1..*** Un script puede ser utilizado en múltiples ejecuciones.

### **AplicaciónMonitoreada — ScriptPowerShell**

- **1..1** Cada aplicación utiliza un script específico para su health check.

### **Usuario — AplicaciónMonitoreada / Scripts / Ejecuciones**

- **Acceso controlado por rol** No existe relación directa en el modelo de dominio, pero sí en la capa de seguridad.

# 4. Regla de Negocio Principal

La regla de negocio determina el **estado final** de una ejecución combinando:

- Código de salida del script (`exitCode`)
- Tiempo de respuesta (`responseTimeMs`)
- Resultado JSON (`resultadoJson`)
- Umbral configurado en la aplicación (`umbralRespuestaMs`)

Ejemplo simplificado:

Código

```
Si exitCode != 0 → ERROR
Si responseTimeMs > umbralRespuestaMs → WARNING
Si resultadoJson.status == "OK" → OK
Caso contrario → ERROR
```

Esta regla se implementa en la capa de servicios y se prueba con JUnit.

# 5. DTOs del Sistema

Los DTO se utilizan para:

- Transferir datos entre capas.
- Evitar exponer entidades JPA directamente.
- Simplificar respuestas del dashboard.

### DTO principales:

- `HealthCheckRequestDTO`
- `HealthCheckResponseDTO`
- `AplicacionDTO
- `ScriptDTO`
- `EjecucionDTO`

# 6. Participación del Patrón Bridge en el Dominio

El patrón Bridge se aplica sobre dos dimensiones del dominio:

### **Abstracción: Procesamiento de Health Checks**

- `HealthCheckProcessor` (interfaz)
- `DefaultHealthCheckProcessor`
- `AdvancedHealthCheckProcessor` (futuro)

### **Implementación: Tipo de Aplicación Monitoreada**

- `IISApplication`
- `TomcatApplication`
- `CustomScriptApplication`

Esto permite:

- Cambiar la lógica de procesamiento sin modificar las entidades.
- Agregar nuevos tipos de aplicaciones sin alterar el servicio central.
- Cumplir el requisito académico del patrón GoF con un caso real.

# 7. Diagrama Conceptual del Dominio

➡️ Ver **[[11_ModeloDominio]]** en la carpeta de diagramas.

# 8. Conclusiones

El modelo de dominio del _Dashboard de Monitoreo de Aplicaciones_ define claramente las entidades, relaciones y reglas de negocio necesarias para cumplir con el alcance académico del parcial y del examen final.

La estructura propuesta permite:

- Registrar ejecuciones de forma consistente.
- Procesar resultados mediante reglas de negocio extensibles.
- Administrar aplicaciones y scripts de manera centralizada.
- Integrar el patrón Bridge sin afectar la persistencia.
- Mantener una arquitectura limpia y escalable.
