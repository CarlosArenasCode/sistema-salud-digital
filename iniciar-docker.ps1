# Script para inicializar el sistema con Docker
# Ejecutar como: .\iniciar-docker.ps1

Write-Host "🏥 Sistema de Salud Digital - Iniciando con Docker" -ForegroundColor Green
Write-Host "=================================================" -ForegroundColor Green

# Verificar si Docker está instalado
try {
    docker --version | Out-Null
    Write-Host "✅ Docker encontrado" -ForegroundColor Green
} catch {
    Write-Host "❌ Docker no está instalado. Por favor instala Docker Desktop." -ForegroundColor Red
    Write-Host "Descarga desde: https://www.docker.com/products/docker-desktop" -ForegroundColor Yellow
    exit 1
}

# Verificar si Docker Compose está disponible
try {
    docker-compose --version | Out-Null
    Write-Host "✅ Docker Compose encontrado" -ForegroundColor Green
} catch {
    Write-Host "❌ Docker Compose no está disponible." -ForegroundColor Red
    exit 1
}

# Detener contenedores existentes si los hay
Write-Host "🔄 Deteniendo contenedores existentes..." -ForegroundColor Yellow
docker-compose -f docker/docker-compose.yml down

# Construir y levantar los servicios
Write-Host "🚀 Construyendo y levantando servicios..." -ForegroundColor Yellow
docker-compose -f docker/docker-compose.yml up --build -d

# Esperar a que los servicios estén listos
Write-Host "⏳ Esperando a que los servicios estén listos..." -ForegroundColor Yellow
Start-Sleep -Seconds 10

# Verificar el estado de los servicios
Write-Host "📋 Estado de los servicios:" -ForegroundColor Cyan
docker-compose -f docker/docker-compose.yml ps

# Mostrar información de acceso
Write-Host ""
Write-Host "🎉 Sistema iniciado correctamente!" -ForegroundColor Green
Write-Host "===============================================" -ForegroundColor Green
Write-Host "🌐 Frontend: http://localhost:8081" -ForegroundColor Cyan
Write-Host "🔧 Backend API: http://localhost:8080/api" -ForegroundColor Cyan
Write-Host "🗄️  Base de datos: localhost:5432" -ForegroundColor Cyan
Write-Host ""
Write-Host "📊 Para ver logs: docker-compose -f docker/docker-compose.yml logs -f" -ForegroundColor Yellow
Write-Host "🛑 Para detener: docker-compose -f docker/docker-compose.yml down" -ForegroundColor Yellow
Write-Host ""

# Abrir navegador automáticamente
Write-Host "🌐 Abriendo navegador..." -ForegroundColor Green
Start-Process "http://localhost:8081"
