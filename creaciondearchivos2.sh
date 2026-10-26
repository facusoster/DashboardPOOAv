#!/bin/bash

# Ruta corregida apuntando a la subcarpeta real de tu proyecto Spring Boot
BASE=app/dashboard/src/main/java/com/dashboard

# ==========================
# MODEL
# ==========================

mkdir -p $BASE/model

touch $BASE/model/Application.java
touch $BASE/model/Script.java
touch $BASE/model/Execution.java
touch $BASE/model/ExecutionResult.java
touch $BASE/model/User.java
touch $BASE/model/Role.java

# ==========================
# REPOSITORIES
# ==========================

mkdir -p $BASE/repository

touch $BASE/repository/ApplicationRepository.java
touch $BASE/repository/ScriptRepository.java
touch $BASE/repository/ExecutionRepository.java
touch $BASE/repository/ExecutionResultRepository.java
touch $BASE/repository/UserRepository.java

# ==========================
# SERVICES
# ==========================

mkdir -p $BASE/service

touch $BASE/service/ApplicationService.java
touch $BASE/service/ScriptService.java
touch $BASE/service/ExecutionService.java
touch $BASE/service/ExecutionResultService.java
touch $BASE/service/UserService.java
touch $BASE/service/HealthCheckService.java

# ==========================
# DTO
# ==========================

mkdir -p $BASE/dto

touch $BASE/dto/ApplicationDTO.java
touch $BASE/dto/ScriptDTO.java
touch $BASE/dto/ExecutionDTO.java
touch $BASE/dto/ExecutionResultDTO.java

# ==========================
# BRIDGE
# ==========================

mkdir -p $BASE/bridge

touch $BASE/bridge/HealthCheckProcessor.java
touch $BASE/bridge/DefaultHealthCheckProcessor.java

mkdir -p $BASE/bridge/application

touch $BASE/bridge/application/MonitoredApplication.java
touch $BASE/bridge/application/IISApplication.java
touch $BASE/bridge/application/TomcatApplication.java
touch $BASE/bridge/application/CustomApplication.java

# ==========================
# SECURITY
# ==========================

mkdir -p $BASE/security

touch $BASE/security/SecurityConfig.java
touch $BASE/security/UserDetailsServiceImpl.java

# ==========================
# WEB CONTROLLERS
# ==========================

mkdir -p $BASE/controller/web

touch $BASE/controller/web/DashboardController.java
touch $BASE/controller/web/ApplicationController.java
touch $BASE/controller/web/ScriptController.java
touch $BASE/controller/web/ExecutionController.java

# ==========================
# CONFIG
# ==========================

mkdir -p $BASE/config

touch $BASE/config/AppConfig.java

echo "Estructura creada correctamente en la ruta del proyecto."
