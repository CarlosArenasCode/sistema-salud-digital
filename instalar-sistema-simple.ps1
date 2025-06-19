# Script de instalación simplificado - Sin verificación de PostgreSQL
# Para casos donde PostgreSQL está instalado pero psql no está en PATH

Write-Host "=== INSTALADOR SIMPLIFICADO - SISTEMA DE SALUD DIGITAL ===" -ForegroundColor Green

# Variables de configuración
$proyectoPath = Get-Location
$javaMinVersion = "17"

# Función para verificar si un comando existe
function Test-Command($command) {
    try {
        Get-Command $command -ErrorAction Stop
        return $true
    }
    catch {
        return $false
    }
}

# Función para obtener versión de Java
function Get-JavaVersion {
    try {
        $javaOutput = java -version 2>&1
        $versionLine = $javaOutput[0] -replace '"', ''
        $version = ($versionLine -split ' ')[2]
        $majorVersion = ($version -split '\.')[0]
        return [int]$majorVersion
    }
    catch {
        return 0
    }
}

# 1. Verificar Java
Write-Host "`n1. Verificando Java..." -ForegroundColor Cyan
if (Test-Command "java") {
    $javaVersion = Get-JavaVersion
    if ($javaVersion -ge $javaMinVersion) {
        Write-Host "✓ Java $javaVersion encontrado" -ForegroundColor Green
    } else {
        Write-Host "✗ Java $javaVersion es demasiado antiguo (requerido: $javaMinVersion+)" -ForegroundColor Red
        Write-Host "Descargar Java 17+ desde: https://adoptium.net/temurin/releases/" -ForegroundColor Yellow
        return
    }
} else {
    Write-Host "✗ Java no encontrado" -ForegroundColor Red
    Write-Host "Instalar Java 17+ desde: https://adoptium.net/temurin/releases/" -ForegroundColor Yellow
    return
}

# 2. Verificar Maven
Write-Host "`n2. Verificando Maven..." -ForegroundColor Cyan
if (Test-Command "mvn") {
    $mavenOutput = mvn -version 2>&1
    $mavenVersion = ($mavenOutput[0] -split ' ')[2]
    Write-Host "✓ Maven $mavenVersion encontrado" -ForegroundColor Green
} else {
    Write-Host "✗ Maven no encontrado" -ForegroundColor Red
    Write-Host "Instalar Maven desde: https://maven.apache.org/download.cgi" -ForegroundColor Yellow
    return
}

# 3. Verificar PostgreSQL (método alternativo)
Write-Host "`n3. Verificando PostgreSQL..." -ForegroundColor Cyan
$postgresService = Get-Service -Name "*postgresql*" -ErrorAction SilentlyContinue
if ($postgresService) {
    if ($postgresService.Status -eq "Running") {
        Write-Host "✓ PostgreSQL está ejecutándose" -ForegroundColor Green
    } else {
        Write-Host "⚠ PostgreSQL está instalado pero no ejecutándose" -ForegroundColor Yellow
        Write-Host "Iniciando servicio PostgreSQL..." -ForegroundColor Yellow
        try {
            Start-Service $postgresService.Name
            Write-Host "✓ PostgreSQL iniciado" -ForegroundColor Green
        } catch {
            Write-Host "✗ No se pudo iniciar PostgreSQL automáticamente" -ForegroundColor Red
            Write-Host "Iniciar manualmente desde Servicios de Windows" -ForegroundColor Yellow
        }
    }
} else {
    Write-Host "⚠ No se pudo verificar PostgreSQL automáticamente" -ForegroundColor Yellow
    Write-Host "Asegúrate de que PostgreSQL esté instalado y ejecutándose" -ForegroundColor Yellow
    Write-Host "Continuando con la instalación..." -ForegroundColor Yellow
}

# 4. Verificar puertos
Write-Host "`n4. Verificando puertos..." -ForegroundColor Cyan
$puerto8080 = Get-NetTCPConnection -LocalPort 8080 -ErrorAction SilentlyContinue
if ($puerto8080) {
    Write-Host "⚠ Puerto 8080 está en uso - se liberará automáticamente" -ForegroundColor Yellow
} else {
    Write-Host "✓ Puerto 8080 disponible" -ForegroundColor Green
}

# 5. Compilar proyecto
Write-Host "`n5. Compilando proyecto..." -ForegroundColor Cyan
Set-Location -Path ".\backend"
try {
    Write-Host "Descargando dependencias y compilando..." -ForegroundColor Yellow
    mvn clean compile -q
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✓ Proyecto compilado exitosamente" -ForegroundColor Green
    } else {
        Write-Host "✗ Error compilando el proyecto" -ForegroundColor Red
        Set-Location -Path $proyectoPath
        return
    }
}
catch {
    Write-Host "✗ Error durante la compilación: $_" -ForegroundColor Red
    Set-Location -Path $proyectoPath
    return
}
Set-Location -Path $proyectoPath

Write-Host "`n=== INSTALACIÓN COMPLETADA ===" -ForegroundColor Green
Write-Host "✓ Java y Maven verificados" -ForegroundColor Green
Write-Host "✓ Proyecto compilado" -ForegroundColor Green
Write-Host "⚠ PostgreSQL: Asegúrate de que esté ejecutándose" -ForegroundColor Yellow

Write-Host "`n=== PRÓXIMOS PASOS ===" -ForegroundColor Cyan
Write-Host "1. Verificar que PostgreSQL esté ejecutándose" -ForegroundColor White
Write-Host "2. Ejecutar: .\iniciar-sistema.ps1" -ForegroundColor White
Write-Host "3. Si hay error de BD, revisar credenciales en config.env" -ForegroundColor White

# Preguntar si quiere iniciar
$respuesta = Read-Host "`n¿Desea intentar iniciar el sistema ahora? (s/n)"
if ($respuesta -eq "s" -or $respuesta -eq "S") {
    Write-Host "`nIniciando sistema..." -ForegroundColor Yellow
    if (Test-Path ".\iniciar-sistema.ps1") {
        & ".\iniciar-sistema.ps1"
    } else {
        Write-Host "Iniciando manualmente..." -ForegroundColor Yellow
        Set-Location -Path ".\backend"
        Start-Process pwsh -ArgumentList "-NoExit", "-Command", "mvn spring-boot:run"
        Set-Location -Path $proyectoPath
        Start-Sleep -Seconds 8
        Start-Process "http://localhost:8080/login.html"
    }
}

Write-Host "`n¡Instalación completada! ✨" -ForegroundColor Green
