# Script de automatización para el sistema de salud digital
# Ejecuta backend y frontend en ventanas separadas y abre el navegador en el frontend

# Ruta absoluta de los proyectos
$backendPath = "c:\WorkSpace\Sistema de Salud Digital\backend"
$frontendPath = "c:\WorkSpace\Sistema de Salud Digital\frontend"

# Inicializar la base de datos PostgreSQL antes de iniciar los servicios
$initDbScript = "c:\WorkSpace\Sistema de Salud Digital\inicializar-db.ps1"
if (Test-Path $initDbScript) {
    Write-Host "Verificando e inicializando la base de datos PostgreSQL..."
    pwsh -File $initDbScript
    Write-Host "Verificación de base de datos completada."
} else {
    Write-Host "No se encontró el script de inicialización de base de datos: $initDbScript"
}

# Iniciar backend en nueva ventana de PowerShell, asegurando el directorio correcto
try {
    Start-Process pwsh -ArgumentList "-NoExit", "-Command", "mvn spring-boot:run" -WorkingDirectory $backendPath -WindowStyle Normal
    Start-Sleep -Seconds 2
    $backendStarted = $true
} catch {
    Write-Host "Error iniciando el backend."
    $backendStarted = $false
}

# Iniciar servidor frontend en nueva ventana de PowerShell usando Node.js (puerto 8080) en lugar de Python
try {
    Start-Process pwsh -ArgumentList "-NoExit", "-Command", "cd `"$PSScriptRoot`"; node server-frontend.js" -WorkingDirectory $PSScriptRoot -WindowStyle Normal
    Start-Sleep -Seconds 2
    $frontendStarted = $true
} catch {
    Write-Host "Error iniciando el frontend con Node.js."
    $frontendStarted = $false
}

# Abrir navegador en la página principal del frontend (ahora en el login)
Start-Sleep -Seconds 3
Start-Process "http://localhost:8081/login.html"

Write-Host "Sistema de Salud Digital iniciado."
