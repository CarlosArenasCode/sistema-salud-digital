# Script de automatización para el sistema de salud digital
# Ejecuta backend y frontend en ventanas separadas y abre el navegador en el frontend

# Ruta absoluta de los proyectos
$backendPath = "c:\WorkSpace\Sistema de Salud Digital\backend"
$frontendPath = "c:\WorkSpace\Sistema de Salud Digital\frontend"

# Omitir inicialización de base de datos para no modificar la BD
Write-Host "Omitiendo verificación e inicialización de la base de datos."

# Cambiar al directorio raíz del proyecto
Set-Location -Path 'C:\WorkSpace\Sistema de Salud Digital'

# Liberar puerto 8081 si está en uso
$puertoFront = 8081
$proceso = (Get-NetTCPConnection -LocalPort $puertoFront -ErrorAction SilentlyContinue).OwningProcess
if ($proceso) {
    Write-Host "Puerto $puertoFront en uso por PID $proceso. Matando proceso..."
    Stop-Process -Id $proceso -Force -ErrorAction SilentlyContinue
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

# Iniciar servidor frontend en nueva ventana de PowerShell usando Node.js
try {
    Start-Process pwsh -ArgumentList "-NoExit", "-Command", "node server-frontend.js" -WorkingDirectory $PSScriptRoot -WindowStyle Normal
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
