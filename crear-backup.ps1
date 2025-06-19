# Script para crear backup completo de la base de datos
# Genera un archivo SQL con todos los datos y estructura

Write-Host "=== GENERADOR DE BACKUP - SISTEMA SALUD DIGITAL ===" -ForegroundColor Green

# Configuración
$database = "sistema_salud"
$usuario = "postgres"
$contrasena = "1234"
$host = "localhost"
$puerto = "5432"

# Generar nombre de archivo con timestamp
$timestamp = Get-Date -Format "yyyyMMdd_HHmmss"
$backupFile = "backup_sistema_salud_$timestamp.sql"

# Crear directorio de backups si no existe
if (-not (Test-Path "backups")) {
    New-Item -ItemType Directory -Path "backups" | Out-Null
    Write-Host "✓ Directorio 'backups' creado" -ForegroundColor Green
}

$backupPath = "backups\$backupFile"

Write-Host "`nCreando backup de la base de datos..." -ForegroundColor Cyan
Write-Host "Base de datos: $database" -ForegroundColor White
Write-Host "Archivo destino: $backupPath" -ForegroundColor White

# Verificar conexión
Write-Host "`nVerificando conexión..." -ForegroundColor Yellow
$env:PGPASSWORD = $contrasena
$connectionTest = psql -U $usuario -h $host -p $puerto -d $database -c "SELECT 1;" -t 2>&1

if ($LASTEXITCODE -ne 0) {
    Write-Host "✗ Error conectando a la base de datos" -ForegroundColor Red
    Write-Host "Verificar que PostgreSQL esté ejecutándose y la base de datos exista" -ForegroundColor Yellow
    return
}

Write-Host "✓ Conexión exitosa" -ForegroundColor Green

# Crear backup completo (estructura + datos)
Write-Host "`nGenerando backup completo..." -ForegroundColor Cyan
$env:PGPASSWORD = $contrasena

# Opciones de pg_dump:
# --clean: incluye comandos DROP antes de CREATE
# --if-exists: usa IF EXISTS en los DROP commands
# --create: incluye comando CREATE DATABASE
# --verbose: muestra progreso detallado
pg_dump -U $usuario -h $host -p $puerto --clean --if-exists --create --verbose $database > $backupPath

if ($LASTEXITCODE -eq 0) {
    Write-Host "✓ Backup creado exitosamente" -ForegroundColor Green
    
    # Verificar tamaño del archivo
    $fileInfo = Get-Item $backupPath
    $sizeKB = [math]::Round($fileInfo.Length / 1024, 2)
    $sizeMB = [math]::Round($fileInfo.Length / 1024 / 1024, 2)
    
    Write-Host "`n=== INFORMACIÓN DEL BACKUP ===" -ForegroundColor Green
    Write-Host "Archivo: $backupPath" -ForegroundColor White
    Write-Host "Tamaño: $sizeKB KB ($sizeMB MB)" -ForegroundColor White
    Write-Host "Fecha: $(Get-Date)" -ForegroundColor White
    
    # Mostrar contenido del backup (primeras líneas)
    Write-Host "`n=== VISTA PREVIA DEL BACKUP ===" -ForegroundColor Cyan
    Get-Content $backupPath -TotalCount 10 | ForEach-Object {
        Write-Host $_ -ForegroundColor Gray
    }
    Write-Host "..." -ForegroundColor Gray
    
    # Contar tablas en el backup
    $tableCount = (Get-Content $backupPath | Select-String "CREATE TABLE").Count
    Write-Host "`n✓ Tablas respaldadas: $tableCount" -ForegroundColor Green
    
    # Mostrar estadísticas de datos
    Write-Host "`n=== ESTADÍSTICAS DE DATOS ===" -ForegroundColor Cyan
    
    $tables = @("pacientes", "medicos", "citas", "medicamentos", "usuarios", "recetas", "historial_medico")
    foreach ($table in $tables) {
        $env:PGPASSWORD = $contrasena
        $count = psql -U $usuario -h $host -p $puerto -d $database -c "SELECT COUNT(*) FROM $table;" -t 2>$null
        if ($LASTEXITCODE -eq 0 -and $count) {
            Write-Host "  $table`: $($count.Trim()) registros" -ForegroundColor White
        }
    }
    
} else {
    Write-Host "✗ Error creando el backup" -ForegroundColor Red
    return
}

# Limpiar backups antiguos (mantener solo los últimos 5)
Write-Host "`n=== LIMPIEZA DE BACKUPS ANTIGUOS ===" -ForegroundColor Cyan
$oldBackups = Get-ChildItem -Path "backups\backup_sistema_salud_*.sql" | Sort-Object LastWriteTime -Descending

if ($oldBackups.Count -gt 5) {
    $backupsToDelete = $oldBackups | Select-Object -Skip 5
    Write-Host "Eliminando backups antiguos (manteniendo los 5 más recientes)..." -ForegroundColor Yellow
    
    foreach ($backup in $backupsToDelete) {
        Remove-Item $backup.FullName -Force
        Write-Host "  Eliminado: $($backup.Name)" -ForegroundColor Gray
    }
    
    Write-Host "✓ Limpieza completada" -ForegroundColor Green
} else {
    Write-Host "✓ No hay backups antiguos que eliminar" -ForegroundColor Green
}

# Mostrar todos los backups disponibles
Write-Host "`n=== BACKUPS DISPONIBLES ===" -ForegroundColor Cyan
$allBackups = Get-ChildItem -Path "backups\backup_sistema_salud_*.sql" | Sort-Object LastWriteTime -Descending

if ($allBackups) {
    foreach ($backup in $allBackups) {
        $size = [math]::Round($backup.Length / 1024, 1)
        Write-Host "  $($backup.Name) - $size KB - $($backup.LastWriteTime)" -ForegroundColor White
    }
} else {
    Write-Host "  No hay backups disponibles" -ForegroundColor Gray
}

Write-Host "`n=== INSTRUCCIONES DE USO ===" -ForegroundColor Green
Write-Host "Para restaurar este backup en otra computadora:" -ForegroundColor White
Write-Host "1. Copiar el archivo: $backupPath" -ForegroundColor Cyan
Write-Host "2. Ejecutar: .\migrar-base-datos.ps1" -ForegroundColor Cyan
Write-Host "3. Seleccionar el archivo cuando se solicite" -ForegroundColor Cyan

Write-Host "`nPara restaurar manualmente:" -ForegroundColor White
Write-Host "psql -U postgres -h localhost -d postgres -f `"$backupPath`"" -ForegroundColor Cyan

Write-Host "`n¡Backup completado exitosamente! 🎉" -ForegroundColor Green
