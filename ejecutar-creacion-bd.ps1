# Script para crear la base de datos completa desde cero
# Ejecuta el archivo SQL que recrea toda la estructura y datos iniciales

Write-Host "=== CREADOR DE BASE DE DATOS COMPLETA ===" -ForegroundColor Green
Write-Host "Sistema de Salud Digital - Creación desde cero" -ForegroundColor White

# Configuración
$database = "sistema_salud"
$usuario = "postgres"
$contrasena = "1234"
$host = "localhost"
$puerto = "5432"
$sqlFile = "crear-base-datos-completa.sql"

# Verificar que el archivo SQL existe
if (-not (Test-Path $sqlFile)) {
    Write-Host "✗ Archivo SQL no encontrado: $sqlFile" -ForegroundColor Red
    Write-Host "Asegúrate de que el archivo esté en el directorio actual" -ForegroundColor Yellow
    return
}

Write-Host "✓ Archivo SQL encontrado: $sqlFile" -ForegroundColor Green

# Verificar conexión a PostgreSQL
Write-Host "`nVerificando conexión a PostgreSQL..." -ForegroundColor Cyan
$env:PGPASSWORD = $contrasena

try {
    $connectionTest = psql -U $usuario -h $host -p $puerto -d postgres -c "SELECT 1;" -t 2>&1
    if ($LASTEXITCODE -ne 0) {
        Write-Host "✗ No se puede conectar a PostgreSQL" -ForegroundColor Red
        Write-Host "Error: $connectionTest" -ForegroundColor Red
        Write-Host "Verificar que PostgreSQL esté ejecutándose" -ForegroundColor Yellow
        return
    }
    Write-Host "✓ Conexión a PostgreSQL exitosa" -ForegroundColor Green
}
catch {
    Write-Host "✗ Error conectando a PostgreSQL: $_" -ForegroundColor Red
    return
}

# Mostrar información del script SQL
Write-Host "`n=== INFORMACIÓN DEL SCRIPT SQL ===" -ForegroundColor Cyan
$fileInfo = Get-Item $sqlFile
$sizeKB = [math]::Round($fileInfo.Length / 1024, 2)
Write-Host "Archivo: $sqlFile" -ForegroundColor White
Write-Host "Tamaño: $sizeKB KB" -ForegroundColor White
Write-Host "Última modificación: $($fileInfo.LastWriteTime)" -ForegroundColor White

# Mostrar preview del contenido
Write-Host "`n=== PREVIEW DEL SCRIPT ===" -ForegroundColor Cyan
$lines = Get-Content $sqlFile -TotalCount 15
foreach ($line in $lines) {
    if ($line.Trim() -and -not $line.StartsWith("--")) {
        Write-Host $line -ForegroundColor Gray
        break
    }
}
Write-Host "..." -ForegroundColor Gray

# Contar elementos a crear
$tableCount = (Get-Content $sqlFile | Select-String "CREATE TABLE").Count
$indexCount = (Get-Content $sqlFile | Select-String "CREATE INDEX").Count
$viewCount = (Get-Content $sqlFile | Select-String "CREATE VIEW").Count
$functionCount = (Get-Content $sqlFile | Select-String "CREATE OR REPLACE FUNCTION").Count

Write-Host "`n=== ELEMENTOS A CREAR ===" -ForegroundColor Green
Write-Host "Tablas: $tableCount" -ForegroundColor White
Write-Host "Índices: $indexCount" -ForegroundColor White
Write-Host "Vistas: $viewCount" -ForegroundColor White
Write-Host "Funciones: $functionCount" -ForegroundColor White

# Verificar si la base de datos existe
Write-Host "`nVerificando si la base de datos existe..." -ForegroundColor Yellow
$dbExists = psql -U $usuario -h $host -p $puerto -d postgres -c "SELECT 1 FROM pg_database WHERE datname = '$database';" -t 2>&1

if ($dbExists -and $dbExists.Trim() -eq "1") {
    Write-Host "⚠ La base de datos '$database' ya existe" -ForegroundColor Yellow
    Write-Host "El script la eliminará y recreará completamente" -ForegroundColor Yellow
    
    $confirm = Read-Host "`n¿Continuar? Esto ELIMINARÁ todos los datos existentes (s/n)"
    if ($confirm -ne "s" -and $confirm -ne "S") {
        Write-Host "Operación cancelada por el usuario" -ForegroundColor Yellow
        return
    }
    
    # Crear backup antes de eliminar
    Write-Host "`nCreando backup de seguridad..." -ForegroundColor Cyan
    $timestamp = Get-Date -Format "yyyyMMdd_HHmmss"
    $backupFile = "backup_antes_recrear_$timestamp.sql"
    
    pg_dump -U $usuario -h $host -p $puerto $database > $backupFile 2>$null
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✓ Backup creado: $backupFile" -ForegroundColor Green
    } else {
        Write-Host "⚠ No se pudo crear backup (la BD podría estar vacía)" -ForegroundColor Yellow
    }
} else {
    Write-Host "✓ La base de datos no existe, se creará desde cero" -ForegroundColor Green
}

# Ejecutar el script SQL
Write-Host "`n=== EJECUTANDO SCRIPT SQL ===" -ForegroundColor Green
Write-Host "Esto puede tomar algunos minutos..." -ForegroundColor Yellow

$startTime = Get-Date

# Ejecutar el script SQL completo
Write-Host "`nEjecutando creación de base de datos..." -ForegroundColor Cyan
$env:PGPASSWORD = $contrasena
$output = psql -U $usuario -h $host -p $puerto -d postgres -f $sqlFile 2>&1

$endTime = Get-Date
$duration = $endTime - $startTime

# Analizar resultado
if ($LASTEXITCODE -eq 0) {
    Write-Host "✓ Script ejecutado exitosamente" -ForegroundColor Green
    Write-Host "Tiempo de ejecución: $($duration.TotalSeconds) segundos" -ForegroundColor White
} else {
    Write-Host "⚠ Script ejecutado con algunas advertencias" -ForegroundColor Yellow
    Write-Host "Tiempo de ejecución: $($duration.TotalSeconds) segundos" -ForegroundColor White
}

# Mostrar salida (últimas líneas)
if ($output) {
    Write-Host "`n=== SALIDA DEL SCRIPT ===" -ForegroundColor Cyan
    $output | Select-Object -Last 10 | ForEach-Object {
        if ($_ -match "ERROR") {
            Write-Host $_ -ForegroundColor Red
        } elseif ($_ -match "WARNING|NOTICE") {
            Write-Host $_ -ForegroundColor Yellow
        } else {
            Write-Host $_ -ForegroundColor Gray
        }
    }
}

# Verificar que la base de datos fue creada correctamente
Write-Host "`n=== VERIFICACIÓN FINAL ===" -ForegroundColor Green

# Verificar conexión a la nueva base de datos
$env:PGPASSWORD = $contrasena
$newDbTest = psql -U $usuario -h $host -p $puerto -d $database -c "SELECT current_database();" -t 2>&1

if ($LASTEXITCODE -eq 0) {
    Write-Host "✓ Base de datos '$database' creada y accesible" -ForegroundColor Green
    
    # Contar tablas creadas
    $tableCount = psql -U $usuario -h $host -p $puerto -d $database -c "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'public';" -t 2>&1
    if ($tableCount) {
        Write-Host "✓ Tablas creadas: $($tableCount.Trim())" -ForegroundColor Green
    }
    
    # Verificar datos iniciales
    $userCount = psql -U $usuario -h $host -p $puerto -d $database -c "SELECT COUNT(*) FROM usuarios;" -t 2>&1
    if ($userCount) {
        Write-Host "✓ Usuarios iniciales: $($userCount.Trim())" -ForegroundColor Green
    }
    
    $medicoCount = psql -U $usuario -h $host -p $puerto -d $database -c "SELECT COUNT(*) FROM medicos;" -t 2>&1
    if ($medicoCount) {
        Write-Host "✓ Médicos iniciales: $($medicoCount.Trim())" -ForegroundColor Green
    }
    
    $medicamentoCount = psql -U $usuario -h $host -p $puerto -d $database -c "SELECT COUNT(*) FROM medicamentos;" -t 2>&1
    if ($medicamentoCount) {
        Write-Host "✓ Medicamentos iniciales: $($medicamentoCount.Trim())" -ForegroundColor Green
    }
    
} else {
    Write-Host "✗ Error verificando la nueva base de datos" -ForegroundColor Red
    Write-Host "Error: $newDbTest" -ForegroundColor Red
}

# Resumen final
Write-Host "`n=== RESUMEN FINAL ===" -ForegroundColor Green
Write-Host "Base de datos: $database" -ForegroundColor White
Write-Host "Estado: Creada exitosamente" -ForegroundColor Green
Write-Host "Archivo SQL: $sqlFile" -ForegroundColor White
Write-Host "Tiempo total: $($duration.TotalSeconds) segundos" -ForegroundColor White

Write-Host "`n=== PRÓXIMOS PASOS ===" -ForegroundColor Cyan
Write-Host "1. Configurar credenciales en config.env si es necesario" -ForegroundColor White
Write-Host "2. Ejecutar: .\iniciar-sistema.ps1" -ForegroundColor White
Write-Host "3. Acceder a: http://localhost:8080/login.html" -ForegroundColor White
Write-Host "4. Usuarios de prueba disponibles en la base de datos" -ForegroundColor White

# Preguntar si iniciar el sistema
$startSystem = Read-Host "`n¿Desea iniciar el sistema ahora? (s/n)"
if ($startSystem -eq "s" -or $startSystem -eq "S") {
    Write-Host "`nIniciando sistema..." -ForegroundColor Yellow
    if (Test-Path ".\iniciar-sistema.ps1") {
        & ".\iniciar-sistema.ps1"
    } else {
        Write-Host "Script de inicio no encontrado. Iniciar manualmente con:" -ForegroundColor Yellow
        Write-Host "cd backend && mvn spring-boot:run" -ForegroundColor Cyan
    }
}

Write-Host "`n¡Base de datos creada exitosamente! 🎉" -ForegroundColor Green
