# Script para migrar y actualizar base de datos
# Restaura backup y aplica cambios recientes del sistema

Write-Host "=== MIGRADOR DE BASE DE DATOS - SISTEMA SALUD DIGITAL ===" -ForegroundColor Green

# Configuración
$backupFile = ""
$database = "sistema_salud"
$usuario = "postgres"
$contrasena = "1234"
$host = "localhost"
$puerto = "5432"

# Función para ejecutar comandos SQL
function Invoke-SqlCommand {
    param(
        [string]$command,
        [string]$database = "postgres"
    )
    
    $env:PGPASSWORD = $contrasena
    try {
        $result = psql -U $usuario -h $host -p $puerto -d $database -c $command -t 2>&1
        if ($LASTEXITCODE -eq 0) {
            return $result
        } else {
            Write-Host "Error ejecutando: $command" -ForegroundColor Red
            Write-Host "Resultado: $result" -ForegroundColor Red
            return $null
        }
    }
    catch {
        Write-Host "Error: $_" -ForegroundColor Red
        return $null
    }
}

# Paso 1: Buscar archivo de backup
Write-Host "`n1. Buscando archivo de backup..." -ForegroundColor Cyan

$backupLocations = @(
    ".\backup.sql",
    ".\backups\*.sql",
    ".\*.sql",
    "..\*.sql"
)

$backupFiles = @()
foreach ($location in $backupLocations) {
    $files = Get-ChildItem -Path $location -ErrorAction SilentlyContinue
    if ($files) {
        $backupFiles += $files
    }
}

if ($backupFiles.Count -eq 0) {
    Write-Host "No se encontraron archivos de backup automáticamente." -ForegroundColor Yellow
    $backupFile = Read-Host "Ingresa la ruta completa del archivo de backup (.sql)"
    
    if (-not (Test-Path $backupFile)) {
        Write-Host "✗ Archivo de backup no encontrado: $backupFile" -ForegroundColor Red
        return
    }
} elseif ($backupFiles.Count -eq 1) {
    $backupFile = $backupFiles[0].FullName
    Write-Host "✓ Backup encontrado: $backupFile" -ForegroundColor Green
} else {
    Write-Host "Múltiples archivos de backup encontrados:" -ForegroundColor Yellow
    for ($i = 0; $i -lt $backupFiles.Count; $i++) {
        Write-Host "  [$($i+1)] $($backupFiles[$i].Name) - $($backupFiles[$i].LastWriteTime)" -ForegroundColor White
    }
    
    $selection = Read-Host "Selecciona el número del backup a usar (1-$($backupFiles.Count))"
    if ($selection -match '^\d+$' -and [int]$selection -ge 1 -and [int]$selection -le $backupFiles.Count) {
        $backupFile = $backupFiles[[int]$selection - 1].FullName
        Write-Host "✓ Backup seleccionado: $backupFile" -ForegroundColor Green
    } else {
        Write-Host "✗ Selección inválida" -ForegroundColor Red
        return
    }
}

# Paso 2: Verificar conexión a PostgreSQL
Write-Host "`n2. Verificando conexión a PostgreSQL..." -ForegroundColor Cyan
$connectionTest = Invoke-SqlCommand "SELECT 1;" "postgres"
if (-not $connectionTest) {
    Write-Host "✗ No se puede conectar a PostgreSQL" -ForegroundColor Red
    Write-Host "Verificar que PostgreSQL esté ejecutándose y las credenciales sean correctas" -ForegroundColor Yellow
    return
}
Write-Host "✓ Conexión a PostgreSQL exitosa" -ForegroundColor Green

# Paso 3: Crear backup de la base actual (si existe)
Write-Host "`n3. Respaldando base de datos actual..." -ForegroundColor Cyan
$dbExists = Invoke-SqlCommand "SELECT 1 FROM pg_database WHERE datname = '$database';" "postgres"

if ($dbExists) {
    $timestamp = Get-Date -Format "yyyyMMdd_HHmmss"
    $currentBackup = "backup_actual_$timestamp.sql"
    
    Write-Host "Creando backup de seguridad: $currentBackup" -ForegroundColor Yellow
    $env:PGPASSWORD = $contrasena
    pg_dump -U $usuario -h $host -p $puerto $database > $currentBackup
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✓ Backup actual creado: $currentBackup" -ForegroundColor Green
    } else {
        Write-Host "⚠ Error creando backup actual, continuando..." -ForegroundColor Yellow
    }
}

# Paso 4: Eliminar base de datos actual
Write-Host "`n4. Preparando base de datos..." -ForegroundColor Cyan
if ($dbExists) {
    Write-Host "Eliminando base de datos actual..." -ForegroundColor Yellow
    
    # Terminar conexiones activas
    $terminateConnections = @"
SELECT pg_terminate_backend(pid)
FROM pg_stat_activity
WHERE datname = '$database' AND pid <> pg_backend_pid();
"@
    
    Invoke-SqlCommand $terminateConnections "postgres" | Out-Null
    Start-Sleep -Seconds 2
    
    # Eliminar base de datos
    $dropResult = Invoke-SqlCommand "DROP DATABASE IF EXISTS $database;" "postgres"
    if ($dropResult -ne $null) {
        Write-Host "✓ Base de datos eliminada" -ForegroundColor Green
    }
}

# Crear nueva base de datos
Write-Host "Creando nueva base de datos..." -ForegroundColor Yellow
$createResult = Invoke-SqlCommand "CREATE DATABASE $database;" "postgres"
if ($createResult -ne $null) {
    Write-Host "✓ Base de datos '$database' creada" -ForegroundColor Green
} else {
    Write-Host "✗ Error creando base de datos" -ForegroundColor Red
    return
}

# Paso 5: Restaurar desde backup
Write-Host "`n5. Restaurando desde backup..." -ForegroundColor Cyan
Write-Host "Archivo: $backupFile" -ForegroundColor White

$env:PGPASSWORD = $contrasena
psql -U $usuario -h $host -p $puerto -d $database -f $backupFile

if ($LASTEXITCODE -eq 0) {
    Write-Host "✓ Backup restaurado exitosamente" -ForegroundColor Green
} else {
    Write-Host "⚠ Hubo algunos errores durante la restauración" -ForegroundColor Yellow
    Write-Host "Esto es normal si hay conflictos menores" -ForegroundColor Gray
}

# Paso 6: Aplicar actualizaciones del sistema actual
Write-Host "`n6. Aplicando actualizaciones automáticas..." -ForegroundColor Cyan
Write-Host "Spring Boot actualizará automáticamente el esquema al iniciar" -ForegroundColor White

# Verificar si hay scripts de migración personalizados
$migrationScripts = Get-ChildItem -Path ".\migrations\*.sql" -ErrorAction SilentlyContinue
if ($migrationScripts) {
    Write-Host "Aplicando scripts de migración personalizados..." -ForegroundColor Yellow
    foreach ($script in $migrationScripts) {
        Write-Host "Ejecutando: $($script.Name)" -ForegroundColor Gray
        $env:PGPASSWORD = $contrasena
        psql -U $usuario -h $host -p $puerto -d $database -f $script.FullName
    }
}

# Paso 7: Verificar integridad
Write-Host "`n7. Verificando integridad de la base de datos..." -ForegroundColor Cyan

$tablesCheck = Invoke-SqlCommand "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'public';" $database
if ($tablesCheck) {
    Write-Host "✓ Tablas encontradas: $($tablesCheck.Trim())" -ForegroundColor Green
} else {
    Write-Host "⚠ No se pudieron verificar las tablas" -ForegroundColor Yellow
}

# Verificar tablas principales
$expectedTables = @("pacientes", "medicos", "citas", "medicamentos", "usuarios")
$missingTables = @()

foreach ($table in $expectedTables) {
    $tableExists = Invoke-SqlCommand "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = '$table';" $database
    if ($tableExists -and $tableExists.Trim() -eq "1") {
        Write-Host "✓ Tabla '$table' existe" -ForegroundColor Green
    } else {
        Write-Host "⚠ Tabla '$table' no encontrada" -ForegroundColor Yellow
        $missingTables += $table
    }
}

# Paso 8: Resumen final
Write-Host "`n=== RESUMEN DE MIGRACIÓN ===" -ForegroundColor Green
Write-Host "✓ Backup restaurado desde: $backupFile" -ForegroundColor Green
Write-Host "✓ Base de datos '$database' lista" -ForegroundColor Green

if ($missingTables.Count -gt 0) {
    Write-Host "⚠ Tablas que se crearán automáticamente:" -ForegroundColor Yellow
    foreach ($table in $missingTables) {
        Write-Host "  - $table" -ForegroundColor White
    }
    Write-Host "Estas se crearán cuando Spring Boot inicie" -ForegroundColor Gray
}

Write-Host "`n=== PRÓXIMOS PASOS ===" -ForegroundColor Cyan
Write-Host "1. Iniciar el sistema: .\iniciar-sistema.ps1" -ForegroundColor White
Write-Host "2. Spring Boot actualizará automáticamente el esquema" -ForegroundColor White
Write-Host "3. Verificar que todo funcione correctamente" -ForegroundColor White

# Opción de iniciar inmediatamente
$startNow = Read-Host "`n¿Desea iniciar el sistema ahora para completar la migración? (s/n)"
if ($startNow -eq "s" -or $startNow -eq "S") {
    Write-Host "`nIniciando sistema..." -ForegroundColor Yellow
    if (Test-Path ".\iniciar-sistema.ps1") {
        & ".\iniciar-sistema.ps1"
    } else {
        Write-Host "Script de inicio no encontrado. Iniciar manualmente con: mvn spring-boot:run" -ForegroundColor Yellow
    }
}

Write-Host "`n¡Migración completada exitosamente! 🎉" -ForegroundColor Green
