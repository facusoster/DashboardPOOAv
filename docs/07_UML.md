
> [!info] 
> **Proyecto:** Dashboard de Monitoreo de Aplicaciones 
> **Materia:** Programación Avanzada (Java + Spring Boot) 
> **Carrera:** Tecnicatura Superior en Análisis de Sistemas 
> **Institución:** Escuela Da Vinci 
> **Profesor:** Lautaro Chiappero 
> **Alumno:** Facundo Soster 
> **Año:** 2026

# 07 · UML

Este documento reúne los **diagramas UML** del proyecto _Dashboard de Monitoreo de Aplicaciones_, organizados según los elementos principales del sistema:

- Casos de uso
    
- Diagrama de clases
    
- Diagramas de secuencia
    
- Diagrama de actividad
    
- Diagrama del patrón GoF aplicado (Bridge)
    

Los diagramas se encuentran en la carpeta:

Código

```
/diagramas
    20_UML_CasosUso.md
    21_UML_Clases.md
    22_UML_SecuenciaLogin.md
    23_UML_SecuenciaHealthCheck.md
    24_UML_ActividadProcesamiento.md
    25_UML_PatronBridge.md
```

Este archivo funciona como índice y documentación explicativa.

# 1. Diagrama de Casos de Uso

**Archivo:** `20_UML_CasosUso.md`

Representa las interacciones entre los actores del sistema:

- Usuario
    
- Administrador
    
- Agente PowerShell
    
- Sistema
    

Casos de uso incluidos:

- Registrar ejecución de health check
    
- Consultar dashboard
    
- Consultar detalle de ejecución
    
- CRUD de aplicaciones monitoreadas
    
- CRUD de scripts PowerShell
    
- Autenticación y autorización
    
- Operación de negocio: determinar estado final
    

Este diagrama se corresponde directamente con el documento **05_CasosUso.md**.

# 2. Diagrama de Clases

**Archivo:** `21_UML_Clases.md`

Incluye las clases principales del dominio y su relación con la arquitectura en capas:

### Clases del dominio

- `AplicacionMonitoreada`
    
- `ScriptPowerShell`
    
- `EjecucionHealthCheck`
    
- `Usuario`
    

### Clases de servicio

- `HealthCheckService`
    
- `AplicacionService`
    
- `ScriptService`
    
- `UsuarioService`
    

### Clases de repositorio

- `AplicacionRepository`
    
- `ScriptRepository`
    
- `EjecucionRepository`
    
- `UsuarioRepository`
    

### Clases del patrón Bridge

- **Abstracción:**
    
    - `HealthCheckProcessor`
        
    - `DefaultHealthCheckProcessor`
        
    - `AdvancedHealthCheckProcessor` (futuro)
        
- **Implementación:**
    
    - `MonitoredApplication` (interfaz)
        
    - `IISApplication`
        
    - `TomcatApplication`
        
    - `CustomScriptApplication`
        

El diagrama muestra:

- Atributos
    
- Métodos relevantes
    
- Relaciones (asociación, composición, dependencia)
    
- Multiplicidades
    
- Jerarquías del patrón Bridge
    

# 3. Diagramas de Secuencia

## 3.1 Secuencia de Login

**Archivo:** `22_UML_SecuenciaLogin.md`

Representa el flujo de autenticación:

1. El usuario envía credenciales.
    
2. Spring Security valida usuario y contraseña.
    
3. Se determina el rol.
    
4. Se redirige al dashboard o se muestra error.
    

Este diagrama es obligatorio para el examen final.

## 3.2 Secuencia de Registro de Health Check

**Archivo:** `23_UML_SecuenciaHealthCheck.md`

Representa el flujo completo del caso de uso CU01:

1. El agente PowerShell envía el JSON.
    
2. El controlador recibe la petición.
    
3. El servicio valida el payload.
    
4. Se aplica la regla de negocio mediante el **Bridge**.
    
5. Se registra la ejecución.
    
6. Se devuelve respuesta al agente.
    

Este diagrama es clave para demostrar:

- Separación de capas
    
- Aplicación del patrón Bridge
    
- Regla de negocio implementada
    

# 4. Diagrama de Actividad

**Archivo:** `24_UML_ActividadProcesamiento.md`

Representa el flujo interno del procesamiento de un health check:

1. Validación del JSON
    
2. Obtención de la aplicación monitoreada
    
3. Obtención del script asociado
    
4. Aplicación de la regla de negocio
    
5. Determinación del estado final
    
6. Persistencia de la ejecución
    
7. Retorno de respuesta
    

Este diagrama es útil para explicar la lógica en la defensa del parcial.

# 5. Diagrama del Patrón GoF: Bridge

**Archivo:** `25_UML_PatronBridge.md`

Este diagrama es **obligatorio** para el parcial y el final.

Representa la estructura del patrón Bridge aplicado al proyecto:

### Abstracción

- `HealthCheckProcessor`
    
- `DefaultHealthCheckProcessor`
    
- `AdvancedHealthCheckProcessor` (futuro)
    

### Implementación

- `MonitoredApplication`
    
- `IISApplication`
    
- `TomcatApplication`
    
- `CustomScriptApplication`
    

### Explicación del Bridge en este proyecto

- La **abstracción** define _cómo_ se procesa un health check.
    
- La **implementación** define _qué tipo de aplicación_ se está monitoreando.
    
- El puente permite cambiar cualquiera de las dos dimensiones sin modificar la otra.
    
- Esto cumple el requisito académico de aplicar un patrón GoF pertinente y justificado.
    

# 6. Relación entre UML y Arquitectura

Los diagramas UML se relacionan directamente con:

- **01_Arquitectura.md**
    
- **02_ModeloDominio.md**
    
- **03_BaseDatos.md**
    
- **05_CasosUso.md**
    

La coherencia entre estos documentos es fundamental para la evaluación del parcial y del examen final.

# 7. Conclusiones

El conjunto de diagramas UML proporciona una visión completa del sistema:

- Qué actores interactúan
    
- Qué funcionalidades se implementan
    
- Cómo se estructura el dominio
    
- Cómo fluye la información
    
- Cómo se aplica el patrón Bridge
    
- Cómo se organiza la arquitectura en capas
    

Estos diagramas serán utilizados en:

- La presentación del parcial
    
- La defensa del examen final
    
- La documentación técnica del proyecto
    
