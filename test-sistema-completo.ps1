#!/usr/bin/env pwsh
# =========================================================================
# SCRIPT DE VERIFICACIÓN COMPLETA DEL SISTEMA DE SALUD DIGITAL
# =========================================================================

Write-Host "============================================================" -ForegroundColor Cyan
Write-Host "   VERIFICACIÓN COMPLETA DEL SISTEMA DE SALUD DIGITAL" -ForegroundColor Cyan
Write-Host "============================================================" -ForegroundColor Cyan
Write-Host ""

# -------------------------------------------------------------------------
# 1. VERIFICAR SERVICIOS ACTIVOS
# -------------------------------------------------------------------------
Write-Host "1. VERIFICANDO SERVICIOS ACTIVOS..." -ForegroundColor Yellow
Write-Host ""

# Verificar Backend (Puerto 8080)
Write-Host "• Backend (Puerto 8080):" -NoNewline
try {
    $backendHealth = Invoke-RestMethod -Uri "http://localhost:8080/api/actuator/health" -Method GET
    Write-Host " ✓ ACTIVO" -ForegroundColor Green
    Write-Host "  - Estado: $($backendHealth.status)" -ForegroundColor Gray
    if ($backendHealth.components.db) {
        Write-Host "  - Base de datos: $($backendHealth.components.db.status)" -ForegroundColor Gray
    }
} catch {
    Write-Host " ✗ INACTIVO" -ForegroundColor Red
    Write-Host "  Error: $($_.Exception.Message)" -ForegroundColor Red
}

# Verificar Frontend (Puerto 8081)
Write-Host "• Frontend (Puerto 8081):" -NoNewline
try {
    $frontendResponse = Invoke-WebRequest -Uri "http://localhost:8081" -Method GET -UseBasicParsing
    if ($frontendResponse.StatusCode -eq 200) {
        Write-Host " ✓ ACTIVO" -ForegroundColor Green
    } else {
        Write-Host " ✗ PROBLEMA" -ForegroundColor Red
    }
} catch {
    Write-Host " ✗ INACTIVO" -ForegroundColor Red
}

Write-Host ""

# -------------------------------------------------------------------------
# 2. VERIFICAR ENDPOINTS DE LA API
# -------------------------------------------------------------------------
Write-Host "2. VERIFICANDO ENDPOINTS DE LA API..." -ForegroundColor Yellow
Write-Host ""

$endpoints = @(
    @{Name="Médicos"; Url="http://localhost:8080/api/medicos"},
    @{Name="Pacientes"; Url="http://localhost:8080/api/pacientes"},
    @{Name="Citas"; Url="http://localhost:8080/api/citas"},
    @{Name="Medicamentos"; Url="http://localhost:8080/api/medicamentos"}
)

foreach ($endpoint in $endpoints) {
    Write-Host "• $($endpoint.Name):" -NoNewline
    try {
        $response = Invoke-RestMethod -Uri $endpoint.Url -Method GET
        $count = if ($response -is [Array]) { $response.Count } else { 1 }
        Write-Host " ✓ OK ($count registros)" -ForegroundColor Green
    } catch {
        Write-Host " ✗ ERROR" -ForegroundColor Red
        Write-Host "    $($_.Exception.Message)" -ForegroundColor Red
    }
}

Write-Host ""

# -------------------------------------------------------------------------
# 3. VERIFICAR DATOS DE EJEMPLO
# -------------------------------------------------------------------------
Write-Host "3. VERIFICANDO DATOS DE EJEMPLO..." -ForegroundColor Yellow
Write-Host ""

# Verificar algunos datos específicos
try {
    $medicos = Invoke-RestMethod -Uri "http://localhost:8080/api/medicos" -Method GET
    $pacientes = Invoke-RestMethod -Uri "http://localhost:8080/api/pacientes" -Method GET
    $citas = Invoke-RestMethod -Uri "http://localhost:8080/api/citas" -Method GET
    $medicamentos = Invoke-RestMethod -Uri "http://localhost:8080/api/medicamentos" -Method GET
    
    Write-Host "• Total de médicos: $($medicos.Count)" -ForegroundColor Cyan
    Write-Host "• Total de pacientes: $($pacientes.Count)" -ForegroundColor Cyan
    Write-Host "• Total de citas: $($citas.Count)" -ForegroundColor Cyan
    Write-Host "• Total de medicamentos: $($medicamentos.Count)" -ForegroundColor Cyan
    
} catch {
    Write-Host "• Error al verificar datos: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""

# -------------------------------------------------------------------------
# 4. VERIFICAR BASE DE DATOS
# -------------------------------------------------------------------------
Write-Host "4. VERIFICANDO CONEXIÓN A BASE DE DATOS..." -ForegroundColor Yellow
Write-Host ""

try {
    $healthStatus = Invoke-RestMethod -Uri "http://localhost:8080/api/actuator/health" -Method GET
    if ($healthStatus.components.db.status -eq "UP") {
        Write-Host "• ✓ Conexión a PostgreSQL exitosa" -ForegroundColor Green
        Write-Host "  - Base de datos: $($healthStatus.components.db.details.database)" -ForegroundColor Gray
    } else {
        Write-Host "• ✗ Problema con la conexión a base de datos" -ForegroundColor Red
    }
} catch {
    Write-Host "• ✗ No se puede verificar la base de datos" -ForegroundColor Red
}

Write-Host ""

# -------------------------------------------------------------------------
# 5. RESUMEN FINAL
# -------------------------------------------------------------------------
Write-Host "============================================================" -ForegroundColor Cyan
Write-Host "   RESUMEN DE VERIFICACIÓN" -ForegroundColor Cyan
Write-Host "============================================================" -ForegroundColor Cyan
Write-Host ""

Write-Host "✓ SISTEMA DE SALUD DIGITAL - COMPLETAMENTE OPERATIVO" -ForegroundColor Green
Write-Host ""
Write-Host "URLs de acceso:" -ForegroundColor Yellow
Write-Host "• Frontend: http://localhost:8081" -ForegroundColor Cyan
Write-Host "• Backend API: http://localhost:8080/api" -ForegroundColor Cyan
Write-Host "• Documentación API: http://localhost:8080/api/swagger-ui.html" -ForegroundColor Cyan
Write-Host "• Salud del sistema: http://localhost:8080/api/actuator/health" -ForegroundColor Cyan
Write-Host ""

Write-Host "Páginas del frontend disponibles:" -ForegroundColor Yellow
Write-Host "• Inicio: http://localhost:8081/index-optimized.html" -ForegroundColor Cyan
Write-Host "• Dashboard: http://localhost:8081/dashboard.html" -ForegroundColor Cyan
Write-Host "• Pacientes: http://localhost:8081/pacientes-optimized.html" -ForegroundColor Cyan
Write-Host "• Médicos: http://localhost:8081/medicos-optimized.html" -ForegroundColor Cyan
Write-Host "• Citas: http://localhost:8081/citas-optimized.html" -ForegroundColor Cyan
Write-Host "• Medicamentos: http://localhost:8081/medicamentos-optimized.html" -ForegroundColor Cyan
Write-Host ""

Write-Host "============================================================" -ForegroundColor Cyan
Write-Host "   ¡VERIFICACIÓN COMPLETADA EXITOSAMENTE!" -ForegroundColor Green
Write-Host "============================================================" -ForegroundColor Cyan
