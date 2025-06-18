# Script para detener todos los servicios del Sistema de Salud Digital
# Ejecutar como: .\detener-sistema.ps1

# Cambiar al directorio raíz del proyecto
Set-Location -Path 'C:\WorkSpace\Sistema de Salud Digital'

# Detener backend Spring Boot (puerto 8080)
$puertoBack = 8080
$procBack = (Get-NetTCPConnection -LocalPort $puertoBack -ErrorAction SilentlyContinue).OwningProcess
if ($procBack) {
    Write-Host "Deteniendo backend Spring Boot (PID $procBack)..."
    Stop-Process -Id $procBack -Force -ErrorAction SilentlyContinue
} else {
    Write-Host "No se encontró ningún servicio escuchando en el puerto $puertoBack."
}

# El sistema ahora funciona completamente en el puerto 8080
# No hay servidor frontend separado que detener

# Cerrar las ventanas de PowerShell secundarias abiertas
$me = $PID
$pwshProcesos = Get-Process -Name pwsh -ErrorAction SilentlyContinue | Where-Object { $_.Id -ne $me }
if ($pwshProcesos) {
    Write-Host "Cerrando ventanas adicionales de PowerShell..."
    $pwshProcesos | Stop-Process -Force -ErrorAction SilentlyContinue
}

Write-Host "Sistema detenido completamente." -ForegroundColor Green
