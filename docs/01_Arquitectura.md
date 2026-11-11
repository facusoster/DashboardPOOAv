
> [!info] 
> **Proyecto:** Dashboard de Monitoreo de Aplicaciones 
> **Materia:** Programación Avanzada (Java + Spring Boot) 
> **Carrera:** Tecnicatura Superior en Análisis de Sistemas 
> **Institución:** Escuela Da Vinci 
> **Profesor:** Lautaro Chiappero 
> **Alumno:** Facundo Soster 
> **Año:** 2026

# 01 · Arquitectura

Este documento describe la arquitectura del proyecto **Dashboard de Monitoreo de Aplicaciones**, detallando los componentes principales, la organización en capas, las tecnologías utilizadas y la forma en que interactúan los distintos elementos del sistema.

La arquitectura se diseñó siguiendo principios de separación de responsabilidades, mantenibilidad, escalabilidad y cumplimiento estricto del alcance académico definido para el parcial y el examen final.

# 1. Visión General de la Arquitectura

El sistema adopta una arquitectura **MVC extendida**, organizada en capas bien definidas:

- **Presentación (View + Controller)**
    
- **Aplicación (Service)**
    
- **Dominio (Entities + DTOs)**
    
- **Persistencia (Repository + DAO)**
    
- **Infraestructura (Configuración, Seguridad, Patrón Bridge)**
    

Cada capa cumple una responsabilidad específica y se comunica únicamente con la capa inmediatamente inferior, garantizando un diseño desacoplado y mantenible.

# 2. Componentes Principales

## 2.1 Aplicación Web (Java + Spring Boot 3)

La aplicación web es el núcleo del sistema. Provee:

- Endpoints HTTP para recibir health checks.
    
- Pantallas Thymeleaf para visualizar métricas.
    
- CRUDs de aplicaciones monitoreadas y scripts.
    
- Autenticación y autorización basada en roles.
    
- Servicios que implementan reglas de negocio.
    

Spring Boot facilita la configuración automática, el manejo de dependencias y la integración con SQLite.

## 2.2 Base de Datos SQLite

Se utiliza **SQLite** como base de datos embebida para garantizar:

- Portabilidad total del ejecutable Windows.
    
- Persistencia local sin dependencias externas.
    
- Simplicidad en el despliegue y demostración académica.
    

La persistencia se implementa mediante:

- JPA/Hibernate
    
- Repositorios Spring Data
    
- DAO para operaciones específicas
    
- Migraciones iniciales mediante scripts SQL
    

## 2.3 Scripts PowerShell (Agente Externo)

Los scripts PowerShell se ejecutan en máquinas Windows y cumplen las siguientes funciones:

- Realizar health checks sobre aplicaciones locales.
    
- Generar un JSON con resultados (exit code, tiempo de respuesta, estado).
    
- Enviar los datos al endpoint `/healthchecks` mediante HTTP POST.
    

El sistema está preparado para recibir múltiples agentes sin necesidad de configuración adicional.

## 2.4 Patrón GoF Aplicado: **Bridge**

El patrón **Bridge** se utiliza para desacoplar dos dimensiones del sistema:

### **Abstracción (Procesamiento de Resultados)**

Define cómo se interpreta un health check y cómo se determina el estado final.

Ejemplos:

- `HealthCheckProcessor`
    
- `DefaultHealthCheckProcessor`
    
- `AdvancedHealthCheckProcessor` (futuro)
    

### **Implementación (Origen del Health Check)**

Define cómo se obtiene o estructura el resultado según el tipo de aplicación monitoreada.

Ejemplos:

- `IISApplication`
    
- `TomcatApplication`
    
- `CustomScriptApplication`
    

Esto permite:

- Agregar nuevos tipos de aplicaciones sin modificar la lógica central.
    
- Cambiar reglas de negocio sin alterar los agentes.
    
- Cumplir el requisito académico del patrón GoF con un caso real y pertinente.
    

➡️ Ver diagramas en **[[Proyecto Laravel/docs/07_UML]]**

# 3. Arquitectura en Capas

La arquitectura sigue el patrón clásico **Controller → Service → Repository → Domain**, recomendado por Spring Boot.

Código

```
┌───────────────────────────────┐
│         Presentación           │
│   (Controllers + Thymeleaf)    │
└───────────────┬───────────────┘
                │
┌───────────────▼───────────────┐
│            Servicios            │
│     (Reglas de negocio +        │
│   Procesamiento de healthchecks)│
└───────────────┬───────────────┘
                │
┌───────────────▼───────────────┐
│           Repositorios          │
│      (Spring Data + DAO)        │
└───────────────┬───────────────┘
                │
┌───────────────▼───────────────┐
│             Dominio             │
│   (Entidades JPA + DTOs)        │
└───────────────────────────────┘
```

# 4. Tecnologías Utilizadas

|Componente|Tecnología|
|---|---|
|Lenguaje|Java 21|
|Framework|Spring Boot 3|
|Motor de vistas|Thymeleaf|
|Base de datos|SQLite embebida|
|ORM|JPA / Hibernate|
|Scripts externos|PowerShell|
|Seguridad|Spring Security + BCrypt|
|Construcción|Maven|
|Documentación|Markdown|

# 5. Módulos del Sistema

## 5.1 Módulo de Monitoreo

- Recepción de health checks.
    
- Validación del payload.
    
- Aplicación de reglas de negocio.
    
- Registro de ejecuciones y logs.
    

## 5.2 Módulo de Dashboard

- Visualización del estado de aplicaciones.
    
- Métricas básicas.
    
- Listado de ejecuciones recientes.
    

## 5.3 Módulo de Administración

- CRUD de aplicaciones monitoreadas.
    
- CRUD de scripts PowerShell.
    
- Gestión de usuarios (examen final).
    

## 5.4 Módulo de Seguridad

- Autenticación con formularios.
    
- Autorización por roles.
    
- Protección CSRF.
    
- Hash de contraseñas con BCrypt.
    

# 6. Flujo General del Sistema

1. El script PowerShell ejecuta un health check.
    
2. Envía los resultados al endpoint `/healthchecks`.
    
3. El controlador recibe el JSON y lo valida.
    
4. El servicio aplica la regla de negocio.
    
5. Se registra la ejecución y los logs.
    
6. El dashboard muestra el estado actualizado.
    

# 7. Diagrama de Arquitectura

➡️ Ver **[[01_ArquitecturaMVC]]** y **[[03_Deployment]]** en la carpeta de diagramas.

# 8. Justificación de la Arquitectura

La arquitectura seleccionada permite:

- Separación clara de responsabilidades.
    
- Escalabilidad para agregar nuevos tipos de aplicaciones monitoreadas.
    
- Portabilidad total para el ejecutable Windows.
    
- Cumplimiento estricto del alcance académico.
    
- Integración simple con agentes externos PowerShell.
    
- Mantenibilidad y claridad en el código.
    

# 9. Conclusiones

La arquitectura del **Dashboard de Monitoreo de Aplicaciones** se diseñó para cumplir con los requisitos del parcial y del examen final, garantizando una base sólida, modular y extensible.

El uso de Spring Boot, SQLite, el patrón Bridge y la separación en capas asegura un sistema mantenible, portable y alineado con las buenas prácticas de desarrollo backend.
