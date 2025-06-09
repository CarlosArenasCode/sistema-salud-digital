# Script mejorado para detener todos los procesos relacionados con el sistema de salud digital
# Detiene backend (Java/Maven), frontend (Node.js) y ventanas de PowerShell abiertas por el sistema

Write-Host "Deteniendo backend (Java/Maven)..."
Get-Process -Name java,mvn -ErrorAction SilentlyContinue | Stop-Process -Force -ErrorAction SilentlyContinue

Write-Host "Deteniendo frontend (Node.js)..."
Get-Process -Name node -ErrorAction SilentlyContinue | Stop-Process -Force -ErrorAction SilentlyContinue

Write-Host "Deteniendo ventanas de PowerShell abiertas por el sistema..."
# Cierra todas las ventanas de pwsh excepto la actual
$myId = $PID
Get-Process -Name pwsh -ErrorAction SilentlyContinue | Where-Object { $_.Id -ne $myId } | Stop-Process -Force -ErrorAction SilentlyContinue

Write-Host "Todos los procesos del Sistema de Salud Digital han sido detenidos."
