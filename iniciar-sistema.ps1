# Script de automatización para el sistema de salud digital - VERSIÓN UNIFICADA
# Solo ejecuta backend (que ahora también sirve el frontend) en un solo puerto

# Ruta absoluta del proyecto backend
$backendPath = "c:\WorkSpace\Sistema de Salud Digital\backend"

# Omitir inicialización de base de datos para no modificar la BD
Write-Host "Omitiendo verificación e inicialización de la base de datos."

# Cambiar al directorio raíz del proyecto
Set-Location -Path 'C:\WorkSpace\Sistema de Salud Digital'

# Liberar puerto 8080 si está en uso (ahora solo necesitamos uno)
$puerto = 8080
$proceso = (Get-NetTCPConnection -LocalPort $puerto -ErrorAction SilentlyContinue).OwningProcess
if ($proceso) {
    Write-Host "Puerto $puerto en uso por PID $proceso. Matando proceso..."
    Stop-Process -Id $proceso -Force -ErrorAction SilentlyContinue
}

# Iniciar backend en nueva ventana de PowerShell (ahora sirve también el frontend)
try {
    Start-Process pwsh -ArgumentList "-NoExit", "-Command", "mvn spring-boot:run" -WorkingDirectory $backendPath -WindowStyle Normal
    Start-Sleep -Seconds 5
    Write-Host "Backend iniciado exitosamente. Ahora sirve frontend y backend en puerto 8080."
    $backendStarted = $true
} catch {
    Write-Host "Error iniciando el sistema."
    $backendStarted = $false
}

# Abrir navegador en la página principal (ahora todo en puerto 8080)
if ($backendStarted) {
    Start-Sleep -Seconds 3
    Start-Process "http://localhost:8080/login.html"
    Write-Host "Sistema de Salud Digital iniciado exitosamente en puerto 8080."
    Write-Host "Frontend: http://localhost:8080"
    Write-Host "API Backend: http://localhost:8080/api"
} else {
    Write-Host "Error al iniciar el sistema."
}
