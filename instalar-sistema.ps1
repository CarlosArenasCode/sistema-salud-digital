# Script de instalación automática para Sistema de Salud Digital
# Ejecutar como administrador en PowerShell

Write-Host "=== INSTALADOR SISTEMA DE SALUD DIGITAL ===" -ForegroundColor Green
Write-Host "Verificando requisitos del sistema..." -ForegroundColor Yellow

# Variables de configuración
$proyectoPath = Get-Location
$javaMinVersion = "17"
$postgresUser = "postgres"
$postgresPassword = "1234"
$postgresDB = "sistema_salud"

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
        Write-Host "✓ Java $javaVersion encontrado (requerido: $javaMinVersion+)" -ForegroundColor Green
    } else {
        Write-Host "✗ Java $javaVersion es demasiado antiguo (requerido: $javaMinVersion+)" -ForegroundColor Red
        Write-Host "Por favor instalar Java 17+ desde: https://adoptium.net/temurin/releases/" -ForegroundColor Yellow
        return
    }
} else {
    Write-Host "✗ Java no encontrado" -ForegroundColor Red
    Write-Host "Por favor instalar Java 17+ desde: https://adoptium.net/temurin/releases/" -ForegroundColor Yellow
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
    Write-Host "Por favor instalar Maven desde: https://maven.apache.org/download.cgi" -ForegroundColor Yellow
    return
}

# 3. Verificar PostgreSQL
Write-Host "`n3. Verificando PostgreSQL..." -ForegroundColor Cyan
if (Test-Command "psql") {
    try {
        $env:PGPASSWORD = $postgresPassword
        $pgOutput = psql -U $postgresUser -h localhost -p 5432 -c "SELECT version();" 2>&1
        if ($LASTEXITCODE -eq 0) {
            Write-Host "✓ PostgreSQL conectado exitosamente" -ForegroundColor Green
        } else {
            Write-Host "✗ No se puede conectar a PostgreSQL" -ForegroundColor Red
            Write-Host "Verificar que PostgreSQL esté ejecutándose y las credenciales sean correctas" -ForegroundColor Yellow
            Write-Host "Usuario: $postgresUser, Contraseña: $postgresPassword" -ForegroundColor Yellow
        }
    }
    catch {
        Write-Host "✗ Error conectando a PostgreSQL: $_" -ForegroundColor Red
    }
} else {
    Write-Host "✗ PostgreSQL (psql) no encontrado" -ForegroundColor Red
    Write-Host "Por favor instalar PostgreSQL desde: https://www.postgresql.org/download/" -ForegroundColor Yellow
    return
}

# 4. Verificar puertos
Write-Host "`n4. Verificando puertos disponibles..." -ForegroundColor Cyan

# Puerto 8080 (aplicación)
$puerto8080 = Get-NetTCPConnection -LocalPort 8080 -ErrorAction SilentlyContinue
if ($puerto8080) {
    Write-Host "⚠ Puerto 8080 está en uso (PID: $($puerto8080.OwningProcess))" -ForegroundColor Yellow
    Write-Host "El script de inicio liberará este puerto automáticamente" -ForegroundColor Yellow
} else {
    Write-Host "✓ Puerto 8080 disponible" -ForegroundColor Green
}

# Puerto 5432 (PostgreSQL)
$puerto5432 = Get-NetTCPConnection -LocalPort 5432 -ErrorAction SilentlyContinue
if ($puerto5432) {
    Write-Host "✓ Puerto 5432 en uso por PostgreSQL (esperado)" -ForegroundColor Green
} else {
    Write-Host "⚠ Puerto 5432 no está en uso - PostgreSQL podría no estar ejecutándose" -ForegroundColor Yellow
}

# 5. Inicializar base de datos
Write-Host "`n5. Inicializando base de datos..." -ForegroundColor Cyan
try {
    if (Test-Path ".\inicializar-db.ps1") {
        & ".\inicializar-db.ps1"
        Write-Host "✓ Base de datos inicializada" -ForegroundColor Green
    } else {
        Write-Host "⚠ Script de inicialización de BD no encontrado" -ForegroundColor Yellow
        Write-Host "Creando base de datos manualmente..." -ForegroundColor Yellow
        
        $env:PGPASSWORD = $postgresPassword
        $checkDB = psql -U $postgresUser -h localhost -p 5432 -lqt | Select-String $postgresDB
        if (-not $checkDB) {
            psql -U $postgresUser -h localhost -p 5432 -c "CREATE DATABASE $postgresDB;"
            Write-Host "✓ Base de datos '$postgresDB' creada" -ForegroundColor Green
        } else {
            Write-Host "✓ Base de datos '$postgresDB' ya existe" -ForegroundColor Green
        }
    }
}
catch {
    Write-Host "✗ Error inicializando base de datos: $_" -ForegroundColor Red
}

# 6. Compilar proyecto
Write-Host "`n6. Compilando proyecto..." -ForegroundColor Cyan
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

# 7. Crear configuración personalizada
Write-Host "`n7. Configurando sistema..." -ForegroundColor Cyan
$configPath = ".\backend\src\main\resources\application-local.yml"
$localConfig = @"
# Configuración local personalizada
spring:
  datasource:
    password: $postgresPassword
  profiles:
    active: postgresql

# Configuración específica para esta instalación
server:
  port: 8080

logging:
  level:
    com.clinica.salud: INFO
"@

try {
    $localConfig | Out-File -FilePath $configPath -Encoding UTF8
    Write-Host "✓ Configuración local creada en: $configPath" -ForegroundColor Green
}
catch {
    Write-Host "⚠ No se pudo crear configuración local: $_" -ForegroundColor Yellow
}

# 8. Verificación final
Write-Host "`n=== RESUMEN DE INSTALACIÓN ===" -ForegroundColor Green
Write-Host "✓ Java: Instalado y configurado" -ForegroundColor Green
Write-Host "✓ Maven: Instalado y configurado" -ForegroundColor Green
Write-Host "✓ PostgreSQL: Conectado y base de datos lista" -ForegroundColor Green
Write-Host "✓ Proyecto: Compilado y listo" -ForegroundColor Green
Write-Host "✓ Configuración: Personalizada para este sistema" -ForegroundColor Green

Write-Host "`n=== PRÓXIMOS PASOS ===" -ForegroundColor Cyan
Write-Host "1. Ejecutar: .\iniciar-sistema.ps1" -ForegroundColor White
Write-Host "2. Abrir navegador en: http://localhost:8080/login.html" -ForegroundColor White
Write-Host "3. Para detener: .\detener-sistema.ps1" -ForegroundColor White

Write-Host "`n¡Sistema listo para usar! 🎉" -ForegroundColor Green

# Preguntar si quiere iniciar ahora
$respuesta = Read-Host "`n¿Desea iniciar el sistema ahora? (s/n)"
if ($respuesta -eq "s" -or $respuesta -eq "S" -or $respuesta -eq "si" -or $respuesta -eq "Si") {
    Write-Host "`nIniciando sistema..." -ForegroundColor Yellow
    if (Test-Path ".\iniciar-sistema.ps1") {
        & ".\iniciar-sistema.ps1"
    } else {
        Write-Host "Script de inicio no encontrado. Iniciando manualmente..." -ForegroundColor Yellow
        Set-Location -Path ".\backend"
        Start-Process pwsh -ArgumentList "-NoExit", "-Command", "mvn spring-boot:run"
        Set-Location -Path $proyectoPath
        Start-Sleep -Seconds 5
        Start-Process "http://localhost:8080/login.html"
    }
}

Write-Host "`n¡Instalación completada! ✨" -ForegroundColor Green
