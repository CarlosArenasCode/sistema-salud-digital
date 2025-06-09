# Script para detener el sistema Docker
# Ejecutar como: .\detener-docker.ps1

Write-Host "🏥 Sistema de Salud Digital - Deteniendo servicios Docker" -ForegroundColor Red
Write-Host "======================================================" -ForegroundColor Red

# Detener y remover contenedores
Write-Host "🛑 Deteniendo contenedores..." -ForegroundColor Yellow
docker-compose -f docker/docker-compose.yml down

# Mostrar contenedores activos
Write-Host "📋 Contenedores activos:" -ForegroundColor Cyan
docker ps --filter "name=salud-"

Write-Host ""
Write-Host "✅ Sistema detenido correctamente!" -ForegroundColor Green
