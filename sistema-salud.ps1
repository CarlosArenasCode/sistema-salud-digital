# Script para gestionar el Sistema de Salud Digital Optimizado
# Fecha: 8 de Junio de 2025

param(
    [Parameter(Mandatory=$true)]
    [ValidateSet("start", "stop", "status", "restart", "test")]
    [string]$Action
)

$BackendDir = "C:\WorkSpace\Sistema de Salud Digital\backend"
$FrontendDir = "C:\WorkSpace\Sistema de Salud Digital"
$BackendPort = 8080
$FrontendPort = 8082

function Write-Header {
    param([string]$Title)
    Write-Host "`n===========================================" -ForegroundColor Cyan
    Write-Host " $Title" -ForegroundColor Yellow
    Write-Host "===========================================" -ForegroundColor Cyan
}

function Test-Port {
    param([int]$Port)
    try {
        $connection = Test-NetConnection -ComputerName localhost -Port $Port -WarningAction SilentlyContinue
        return $connection.TcpTestSucceeded
    } catch {
        return $false
    }
}

function Start-Backend {
    Write-Host "🚀 Iniciando Backend (Puerto $BackendPort)..." -ForegroundColor Green
    Set-Location $BackendDir
    Start-Process powershell -ArgumentList "-Command", "mvn spring-boot:run" -WindowStyle Minimized
    
    # Esperar a que el backend esté listo
    $attempts = 0
    do {
        Start-Sleep -Seconds 3
        $attempts++
        Write-Host "Verificando backend... (intento $attempts)" -ForegroundColor Yellow
    } while (-not (Test-Port $BackendPort) -and $attempts -lt 20)
    
    if (Test-Port $BackendPort) {
        Write-Host "✅ Backend iniciado correctamente en http://localhost:$BackendPort" -ForegroundColor Green
    } else {
        Write-Host "❌ Error al iniciar el backend" -ForegroundColor Red
    }
}

function Start-Frontend {
    Write-Host "🌐 Iniciando Frontend (Puerto $FrontendPort)..." -ForegroundColor Green
    Set-Location $FrontendDir
    Start-Process powershell -ArgumentList "-Command", "node server-frontend.js" -WindowStyle Minimized
    
    Start-Sleep -Seconds 2
    if (Test-Port $FrontendPort) {
        Write-Host "✅ Frontend iniciado correctamente en http://localhost:$FrontendPort" -ForegroundColor Green
    } else {
        Write-Host "❌ Error al iniciar el frontend" -ForegroundColor Red
    }
}

function Stop-Services {
    Write-Host "🛑 Deteniendo servicios..." -ForegroundColor Yellow
    
    # Detener procesos Java (Backend)
    Get-Process -Name "java" -ErrorAction SilentlyContinue | Stop-Process -Force
    
    # Detener procesos Node (Frontend)
    Get-Process -Name "node" -ErrorAction SilentlyContinue | Stop-Process -Force
    
    Write-Host "✅ Servicios detenidos" -ForegroundColor Green
}

function Show-Status {
    Write-Header "ESTADO DEL SISTEMA DE SALUD DIGITAL"
    
    # Verificar Backend
    if (Test-Port $BackendPort) {
        Write-Host "🟢 Backend: ACTIVO (Puerto $BackendPort)" -ForegroundColor Green
        try {
            $health = Invoke-RestMethod -Uri "http://localhost:$BackendPort/api/health" -Method GET
            Write-Host "   📊 Base de Datos: $($health.database)" -ForegroundColor Cyan
            Write-Host "   🏥 Aplicación: $($health.application)" -ForegroundColor Cyan
            Write-Host "   📦 Versión: $($health.version)" -ForegroundColor Cyan
        } catch {
            Write-Host "   ⚠️  API no responde correctamente" -ForegroundColor Yellow
        }
    } else {
        Write-Host "🔴 Backend: INACTIVO" -ForegroundColor Red
    }
    
    # Verificar Frontend
    if (Test-Port $FrontendPort) {
        Write-Host "🟢 Frontend: ACTIVO (Puerto $FrontendPort)" -ForegroundColor Green
        Write-Host "   🌐 URL: http://localhost:$FrontendPort" -ForegroundColor Cyan
    } else {
        Write-Host "🔴 Frontend: INACTIVO" -ForegroundColor Red
    }
    
    # Mostrar endpoints disponibles
    if (Test-Port $BackendPort) {
        Write-Host "`n📡 ENDPOINTS DISPONIBLES:" -ForegroundColor Magenta
        Write-Host "   • GET  /api/pacientes" -ForegroundColor White
        Write-Host "   • GET  /api/medicos" -ForegroundColor White
        Write-Host "   • GET  /api/citas" -ForegroundColor White
        Write-Host "   • GET  /api/medicamentos" -ForegroundColor White
        Write-Host "   • GET  /api/health" -ForegroundColor White
    }
}

function Test-System {
    Write-Header "PRUEBAS DEL SISTEMA"
    
    if (-not (Test-Port $BackendPort)) {
        Write-Host "❌ Backend no está corriendo. Iniciando pruebas..." -ForegroundColor Red
        return
    }
    
    $endpoints = @(
        "/api/health",
        "/api/pacientes", 
        "/api/medicos",
        "/api/medicamentos"
    )
    
    foreach ($endpoint in $endpoints) {
        try {
            Write-Host "🧪 Probando $endpoint..." -ForegroundColor Yellow
            $response = Invoke-RestMethod -Uri "http://localhost:$BackendPort$endpoint" -Method GET
            Write-Host "   ✅ OK - Respuesta recibida" -ForegroundColor Green
        } catch {
            Write-Host "   ❌ ERROR - $($_.Exception.Message)" -ForegroundColor Red
        }
    }
    
    # Probar frontend
    if (Test-Port $FrontendPort) {
        Write-Host "🧪 Probando Frontend..." -ForegroundColor Yellow
        try {
            $response = Invoke-WebRequest -Uri "http://localhost:$FrontendPort" -Method GET
            Write-Host "   ✅ OK - Frontend responde" -ForegroundColor Green
        } catch {
            Write-Host "   ❌ ERROR - Frontend no responde" -ForegroundColor Red
        }
    }
}

# Ejecutar acción solicitada
switch ($Action) {
    "start" {
        Write-Header "INICIANDO SISTEMA DE SALUD DIGITAL"
        Start-Backend
        Start-Sleep -Seconds 5
        Start-Frontend
        Show-Status
    }
    "stop" {
        Write-Header "DETENIENDO SISTEMA"
        Stop-Services
    }
    "status" {
        Show-Status
    }
    "restart" {
        Write-Header "REINICIANDO SISTEMA"
        Stop-Services
        Start-Sleep -Seconds 3
        Start-Backend
        Start-Sleep -Seconds 5
        Start-Frontend
        Show-Status
    }
    "test" {
        Test-System
    }
}

Write-Host "`n🏥 Sistema de Salud Digital - Versión Optimizada" -ForegroundColor Cyan
Write-Host "📊 Reporte completo disponible en: REPORTE-OPTIMIZACION.md" -ForegroundColor Gray
