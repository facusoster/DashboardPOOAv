
> [!info] 
> **Proyecto:** Dashboard de Monitoreo de Aplicaciones 
> **Materia:** Programación Avanzada (Java + Spring Boot) 
> **Carrera:** Tecnicatura Superior en Análisis de Sistemas 
> **Institución:** Escuela Da Vinci 
> **Profesor:** Lautaro Chiappero 
> **Alumno:** Facundo Soster 
> **Año:** 2026

# 03 · Base de Datos

Este documento describe la estructura de la base de datos utilizada por el proyecto **Dashboard de Monitoreo de Aplicaciones**, incluyendo tablas, tipos de datos, claves primarias, claves foráneas, restricciones y justificación técnica.

La base de datos se implementa utilizando **SQLite**, seleccionada por su portabilidad, simplicidad y compatibilidad con el requisito académico del examen final: entregar un ejecutable Windows sin dependencias externas.

# 1. Justificación Técnica de SQLite

SQLite fue elegida como motor de base de datos por las siguientes razones:

- **Embebida y portable:** No requiere instalación ni servicio externo.
- **Ideal para ejecutables Windows:** Permite distribuir la aplicación con persistencia local.
- **Compatible con JPA/Hibernate:** Permite utilizar entidades, repositorios y consultas estándar.
- **Adecuada para el alcance académico:** No se necesita escalabilidad ni concurrencia avanzada.
- **Persistencia real:** Los datos se conservan entre ejecuciones, cumpliendo el requisito del examen final.

# 2. Modelo Físico de la Base de Datos

El modelo físico se deriva directamente del **DER** definido en el archivo **04_DER.md**.

Las tablas principales son:

1. `aplicaciones_monitoreadas`
2. `scripts_powershell`
3. `ejecuciones_healthcheck`
4. `usuarios`

# 3. Tablas y Estructura

A continuación se detalla la estructura de cada tabla, con tipos de datos compatibles con SQLite.

## 3.1 Tabla: aplicaciones_monitoreadas

sql

```
CREATE TABLE aplicaciones_monitoreadas (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    url TEXT NOT NULL,
    descripcion TEXT,
    tipo TEXT NOT NULL,
    umbral_respuesta_ms INTEGER NOT NULL,
    activo BOOLEAN NOT NULL,
    script_id INTEGER NOT NULL,
    FOREIGN KEY (script_id) REFERENCES scripts_powershell(id)
);
```

### Descripción

- Cada aplicación monitoreada utiliza un script PowerShell específico.
- El campo `umbral_respuesta_ms` se utiliza en la regla de negocio para determinar el estado final.
- `activo` permite habilitar o deshabilitar aplicaciones sin eliminarlas.

## 3.2 Tabla: scripts_powershell

sql

```
CREATE TABLE scripts_powershell (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    contenido TEXT NOT NULL,
    descripcion TEXT,
    version TEXT
);
```

### Descripción

- El contenido del script se almacena como texto completo.
- La tabla permite versionar scripts y reutilizarlos en múltiples aplicaciones.

## 3.3 Tabla: ejecuciones_healthcheck

sql

```
CREATE TABLE ejecuciones_healthcheck (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    fecha_hora TEXT NOT NULL,
    exit_code INTEGER NOT NULL,
    response_time_ms INTEGER NOT NULL,
    resultado_json TEXT NOT NULL,
    estado_final TEXT NOT NULL,
    logs TEXT,
    aplicacion_id INTEGER NOT NULL,
    script_id INTEGER NOT NULL,
    FOREIGN KEY (aplicacion_id) REFERENCES aplicaciones_monitoreadas(id),
    FOREIGN KEY (script_id) REFERENCES scripts_powershell(id)
);
```

### Descripción

- Registra cada ejecución enviada por los agentes PowerShell.
- `estado_final` es el resultado de aplicar la regla de negocio.
- `resultado_json` almacena el payload completo enviado por el script.
- `logs` permite guardar mensajes adicionales.

## 3.4 Tabla: usuarios

sql

```
CREATE TABLE usuarios (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    rol TEXT NOT NULL
);
```

### Descripción

- `password` se almacena utilizando BCrypt.
- `rol` define permisos: `ADMIN` o `USER`.
- La tabla es utilizada por Spring Security.

# 4. Relaciones y Claves Foráneas

|Relación|Tipo|Descripción|
|---|---|---|
|aplicaciones_monitoreadas → scripts_powershell|1 a 1|Cada aplicación utiliza un script.|
|ejecuciones_healthcheck → aplicaciones_monitoreadas|1 a N|Una aplicación tiene múltiples ejecuciones.|
|ejecuciones_healthcheck → scripts_powershell|1 a N|Un script puede ser usado en múltiples ejecuciones.|

SQLite soporta claves foráneas, pero deben habilitarse explícitamente:

sql

```
PRAGMA foreign_keys = ON;
```

Spring Boot se encarga de esto automáticamente.

# 5. Restricciones y Reglas de Integridad

### 5.1 Integridad Referencial

- `script_id` en aplicaciones debe existir en scripts.
- `aplicacion_id` y `script_id` en ejecuciones deben existir en sus tablas respectivas.

### 5.2 Restricciones de Negocio

- Una aplicación debe tener un script asignado.
- Una ejecución debe pertenecer a una aplicación y a un script.
- `estado_final` debe ser uno de: `OK`, `WARNING`, `ERROR`.

### 5.3 Restricciones Técnicas

- SQLite almacena fechas como TEXT en formato ISO-8601.
- No se utilizan tipos avanzados (no soportados por SQLite).
- Los campos TEXT permiten almacenar JSON sin problemas.

# 6. Scripts de Inicialización

Para facilitar la demostración y pruebas, se incluirá un script SQL inicial:

sql

```
INSERT INTO usuarios (username, password, rol)
VALUES ('admin', '<bcrypt>', 'ADMIN');

INSERT INTO scripts_powershell (nombre, contenido, descripcion, version)
VALUES ('Default Script', 'Write-Output "OK"', 'Script base de prueba', '1.0');
```

El archivo se incluirá en:

Código

```
src/main/resources/db/init.sql
```

# 7. Consideraciones para el Ejecutable Windows

El ejecutable final incluirá:

- La base SQLite dentro del paquete.
- Datos ficticios precargados.
- Persistencia real entre ejecuciones.
- Un mecanismo opcional para restaurar la base a estado inicial.

El archivo de base se ubicará en:

Código

```
/data/monitoring.db
```

# 8. Conclusiones

La base de datos diseñada es:

- Simple
- Portable
- Coherente con el modelo de dominio
- Compatible con Spring Boot y JPA
- Adecuada para el parcial y el examen final
- Fácil de distribuir en un ejecutable Windows

El diseño permite registrar ejecuciones, administrar aplicaciones y scripts, y soportar la regla de negocio sin complejidad innecesaria.

