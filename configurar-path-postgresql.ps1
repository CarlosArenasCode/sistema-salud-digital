# Script para encontrar PostgreSQL y agregarlo al PATH automáticamente

Write-Host "=== CONFIGURADOR DE PATH PARA POSTGRESQL ===" -ForegroundColor Green

# Ubicaciones comunes donde puede estar PostgreSQL
$ubicacionesComunes = @(
    "C:\Program Files\PostgreSQL\*\bin",
    "C:\Program Files (x86)\PostgreSQL\*\bin",
    "$env:LOCALAPPDATA\Programs\PostgreSQL\*\bin",
    "$env:APPDATA\PostgreSQL\*\bin"
)

$postgresqlEncontrado = $null

Write-Host "`nBuscando PostgreSQL..." -ForegroundColor Yellow

foreach ($ubicacion in $ubicacionesComunes) {
    $rutas = Get-ChildItem -Path $ubicacion -ErrorAction SilentlyContinue
    if ($rutas) {
        foreach ($ruta in $rutas) {
            $psqlPath = Join-Path $ruta.FullName "psql.exe"
            if (Test-Path $psqlPath) {
                $postgresqlEncontrado = $ruta.FullName
                Write-Host "✓ PostgreSQL encontrado en: $postgresqlEncontrado" -ForegroundColor Green
                break
            }
        }
        if ($postgresqlEncontrado) { break }
    }
}

if (-not $postgresqlEncontrado) {
    Write-Host "✗ No se pudo encontrar PostgreSQL automáticamente" -ForegroundColor Red
    Write-Host "Ubicaciones buscadas:" -ForegroundColor Yellow
    foreach ($ub in $ubicacionesComunes) {
        Write-Host "  - $ub" -ForegroundColor Gray
    }
    
    $rutaManual = Read-Host "`nIngresa la ruta manualmente (o presiona Enter para omitir)"
    if ($rutaManual -and (Test-Path (Join-Path $rutaManual "psql.exe"))) {
        $postgresqlEncontrado = $rutaManual
        Write-Host "✓ Ruta manual verificada: $postgresqlEncontrado" -ForegroundColor Green
    } else {
        Write-Host "Saliendo sin modificar PATH..." -ForegroundColor Yellow
        return
    }
}

# Verificar si ya está en PATH
$currentPath = $env:PATH
if ($currentPath -like "*$postgresqlEncontrado*") {
    Write-Host "✓ PostgreSQL ya está en PATH" -ForegroundColor Green
} else {
    Write-Host "`nAgregando PostgreSQL al PATH..." -ForegroundColor Yellow
    
    # Agregar temporalmente (para esta sesión)
    $env:PATH += ";$postgresqlEncontrado"
    Write-Host "✓ Agregado temporalmente al PATH de esta sesión" -ForegroundColor Green
    
    # Preguntar si agregar permanentemente
    $respuesta = Read-Host "`n¿Agregar permanentemente al PATH del usuario? (s/n)"
    if ($respuesta -eq "s" -or $respuesta -eq "S") {
        try {
            $userPath = [Environment]::GetEnvironmentVariable("PATH", "User")
            if ($userPath -notlike "*$postgresqlEncontrado*") {
                $newUserPath = $userPath + ";$postgresqlEncontrado"
                [Environment]::SetEnvironmentVariable("PATH", $newUserPath, "User")
                Write-Host "✓ PostgreSQL agregado permanentemente al PATH del usuario" -ForegroundColor Green
                Write-Host "⚠ Reinicia PowerShell para que tome efecto en nuevas sesiones" -ForegroundColor Yellow
            } else {
                Write-Host "✓ PostgreSQL ya estaba en PATH permanente" -ForegroundColor Green
            }
        }
        catch {
            Write-Host "✗ Error agregando al PATH permanente: $_" -ForegroundColor Red
            Write-Host "Puedes agregarlo manualmente desde Variables de Entorno" -ForegroundColor Yellow
        }
    }
}

# Probar comandos PostgreSQL
Write-Host "`nProbando comandos PostgreSQL..." -ForegroundColor Cyan
try {
    $version = & psql --version 2>&1
    Write-Host "✓ psql funciona: $version" -ForegroundColor Green
    
    $ready = & pg_isready --help 2>&1
    if ($ready) {
        Write-Host "✓ pg_isready funciona" -ForegroundColor Green
    }
}
catch {
    Write-Host "⚠ Error probando comandos: $_" -ForegroundColor Yellow
}

Write-Host "`n=== RESUMEN ===" -ForegroundColor Green
Write-Host "PostgreSQL encontrado en: $postgresqlEncontrado" -ForegroundColor White
Write-Host "PATH actualizado para esta sesión" -ForegroundColor White
Write-Host "`nAhora puedes usar:" -ForegroundColor Cyan
Write-Host "  psql --version" -ForegroundColor White
Write-Host "  pg_isready -h localhost -p 5432" -ForegroundColor White
Write-Host "  .\instalar-sistema.ps1" -ForegroundColor White

Write-Host "`n¡PATH configurado exitosamente! ✨" -ForegroundColor Green
