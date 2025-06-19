# Script para migrar solo cambios específicos entre bases de datos
# Útil cuando solo hay cambios menores y no quieres restaurar todo

Write-Host "=== MIGRADOR DE CAMBIOS INCREMENTALES ===" -ForegroundColor Green

# Configuración
$database = "sistema_salud"
$usuario = "postgres"
$contrasena = "1234"
$host = "localhost"
$puerto = "5432"

# Función para ejecutar SQL
function Invoke-SqlCommand {
    param([string]$command, [string]$db = $database)
    $env:PGPASSWORD = $contrasena
    try {
        $result = psql -U $usuario -h $host -p $puerto -d $db -c $command -t 2>&1
        return ($LASTEXITCODE -eq 0), $result
    }
    catch {
        return $false, $_.Exception.Message
    }
}

# Verificar conexión
Write-Host "`nVerificando conexión a base de datos..." -ForegroundColor Cyan
$success, $result = Invoke-SqlCommand "SELECT current_database();"
if (-not $success) {
    Write-Host "✗ No se puede conectar a la base de datos" -ForegroundColor Red
    return
}
Write-Host "✓ Conectado a: $($result.Trim())" -ForegroundColor Green

# Menú de opciones de migración
Write-Host "`n=== OPCIONES DE MIGRACIÓN ===" -ForegroundColor Cyan
Write-Host "1. Sincronizar estructura de tablas (ALTER TABLE automático)" -ForegroundColor White
Write-Host "2. Migrar datos específicos (INSERT/UPDATE)" -ForegroundColor White
Write-Host "3. Ejecutar script SQL personalizado" -ForegroundColor White
Write-Host "4. Comparar y mostrar diferencias" -ForegroundColor White
Write-Host "5. Aplicar todas las migraciones automáticas" -ForegroundColor White

$opcion = Read-Host "`nSelecciona una opción (1-5)"

switch ($opcion) {
    "1" {
        Write-Host "`n=== SINCRONIZACIÓN DE ESTRUCTURA ===" -ForegroundColor Green
        Write-Host "Spring Boot sincronizará automáticamente la estructura al iniciar" -ForegroundColor White
        Write-Host "Configuración actual: hibernate.ddl-auto = update" -ForegroundColor Gray
        
        Write-Host "`nIniciando aplicación para sincronizar..." -ForegroundColor Yellow
        if (Test-Path ".\iniciar-sistema.ps1") {
            Write-Host "El sistema se iniciará y Spring Boot actualizará la estructura automáticamente" -ForegroundColor White
            $start = Read-Host "¿Proceder con el inicio? (s/n)"
            if ($start -eq "s" -or $start -eq "S") {
                & ".\iniciar-sistema.ps1"
            }
        }
    }
    
    "2" {
        Write-Host "`n=== MIGRACIÓN DE DATOS ESPECÍFICOS ===" -ForegroundColor Green
        
        # Buscar archivos de datos
        $dataFiles = Get-ChildItem -Path ".\data\*.sql" -ErrorAction SilentlyContinue
        if (-not $dataFiles) {
            $dataFiles = Get-ChildItem -Path ".\*.sql" | Where-Object { $_.Name -like "*data*" -or $_.Name -like "*insert*" }
        }
        
        if ($dataFiles) {
            Write-Host "Archivos de datos encontrados:" -ForegroundColor Yellow
            for ($i = 0; $i -lt $dataFiles.Count; $i++) {
                Write-Host "  [$($i+1)] $($dataFiles[$i].Name)" -ForegroundColor White
            }
            
            $selection = Read-Host "Selecciona el archivo (1-$($dataFiles.Count)) o 'todos'"
            
            if ($selection -eq "todos") {
                foreach ($file in $dataFiles) {
                    Write-Host "Ejecutando: $($file.Name)" -ForegroundColor Cyan
                    $env:PGPASSWORD = $contrasena
                    psql -U $usuario -h $host -p $puerto -d $database -f $file.FullName
                }
            } elseif ($selection -match '^\d+$' -and [int]$selection -ge 1 -and [int]$selection -le $dataFiles.Count) {
                $selectedFile = $dataFiles[[int]$selection - 1]
                Write-Host "Ejecutando: $($selectedFile.Name)" -ForegroundColor Cyan
                $env:PGPASSWORD = $contrasena
                psql -U $usuario -h $host -p $puerto -d $database -f $selectedFile.FullName
            }
        } else {
            Write-Host "No se encontraron archivos de datos" -ForegroundColor Yellow
            Write-Host "Creando plantilla de migración de datos..." -ForegroundColor Cyan
            
            $template = @"
-- Plantilla de migración de datos
-- Ejecutar comandos SQL específicos para migrar datos

-- Ejemplo: Actualizar registros existentes
-- UPDATE pacientes SET telefono = '123-456-7890' WHERE id = 1;

-- Ejemplo: Insertar nuevos registros
-- INSERT INTO medicamentos (nombre, descripcion, precio) VALUES ('Nuevo Medicamento', 'Descripción', 25.50);

-- Ejemplo: Crear índices adicionales
-- CREATE INDEX IF NOT EXISTS idx_pacientes_email ON pacientes(email);

-- Agregar tus comandos SQL aquí:

"@
            $template | Out-File -FilePath "migracion_datos_$(Get-Date -Format 'yyyyMMdd_HHmmss').sql" -Encoding UTF8
            Write-Host "✓ Plantilla creada. Edítala y vuelve a ejecutar esta opción." -ForegroundColor Green
        }
    }
    
    "3" {
        Write-Host "`n=== SCRIPT SQL PERSONALIZADO ===" -ForegroundColor Green
        $scriptPath = Read-Host "Ingresa la ruta del script SQL"
        
        if (Test-Path $scriptPath) {
            Write-Host "Ejecutando script: $scriptPath" -ForegroundColor Cyan
            $env:PGPASSWORD = $contrasena
            psql -U $usuario -h $host -p $puerto -d $database -f $scriptPath
            
            if ($LASTEXITCODE -eq 0) {
                Write-Host "✓ Script ejecutado exitosamente" -ForegroundColor Green
            } else {
                Write-Host "⚠ Hubo algunos errores durante la ejecución" -ForegroundColor Yellow
            }
        } else {
            Write-Host "✗ Archivo no encontrado: $scriptPath" -ForegroundColor Red
        }
    }
    
    "4" {
        Write-Host "`n=== COMPARACIÓN DE ESQUEMAS ===" -ForegroundColor Green
        Write-Host "Comparando estructura actual con la esperada..." -ForegroundColor White
        
        # Verificar tablas principales
        $expectedTables = @(
            @{Name="pacientes"; Columns=@("id","nombres","apellidos","email","telefono","fecha_nacimiento")},
            @{Name="medicos"; Columns=@("id","nombres","apellidos","especialidad","email","telefono")},
            @{Name="citas"; Columns=@("id","paciente_id","medico_id","fecha_hora","estado","motivo")},
            @{Name="medicamentos"; Columns=@("id","nombre","descripcion","precio","stock")},
            @{Name="usuarios"; Columns=@("id","username","password","rol","activo")}
        )
        
        foreach ($table in $expectedTables) {
            $success, $result = Invoke-SqlCommand "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = '$($table.Name)';"
            if ($success -and $result.Trim() -eq "1") {
                Write-Host "✓ Tabla '$($table.Name)' existe" -ForegroundColor Green
                
                # Verificar columnas principales
                foreach ($column in $table.Columns) {
                    $success, $result = Invoke-SqlCommand "SELECT COUNT(*) FROM information_schema.columns WHERE table_name = '$($table.Name)' AND column_name = '$column';"
                    if ($success -and $result.Trim() -eq "1") {
                        Write-Host "  ✓ Columna '$column'" -ForegroundColor Gray
                    } else {
                        Write-Host "  ⚠ Columna '$column' faltante" -ForegroundColor Yellow
                    }
                }
            } else {
                Write-Host "⚠ Tabla '$($table.Name)' no encontrada" -ForegroundColor Yellow
            }
        }
    }
    
    "5" {
        Write-Host "`n=== MIGRACIÓN AUTOMÁTICA COMPLETA ===" -ForegroundColor Green
        Write-Host "Esta opción aplicará todas las migraciones disponibles:" -ForegroundColor White
        Write-Host "1. Estructura de tablas (Spring Boot)" -ForegroundColor Cyan
        Write-Host "2. Scripts de migración personalizados" -ForegroundColor Cyan
        Write-Host "3. Datos iniciales si existen" -ForegroundColor Cyan
        
        $confirm = Read-Host "`n¿Continuar con la migración automática? (s/n)"
        if ($confirm -eq "s" -or $confirm -eq "S") {
            
            # Ejecutar scripts de migración si existen
            $migrationScripts = Get-ChildItem -Path ".\migrations\*.sql" -ErrorAction SilentlyContinue
            if ($migrationScripts) {
                Write-Host "`nEjecutando scripts de migración personalizados..." -ForegroundColor Yellow
                foreach ($script in $migrationScripts) {
                    Write-Host "Ejecutando: $($script.Name)" -ForegroundColor Cyan
                    $env:PGPASSWORD = $contrasena
                    psql -U $usuario -h $host -p $puerto -d $database -f $script.FullName
                }
            }
            
            # Iniciar sistema para aplicar migraciones de Spring Boot
            Write-Host "`nIniciando sistema para aplicar migraciones automáticas..." -ForegroundColor Yellow
            if (Test-Path ".\iniciar-sistema.ps1") {
                & ".\iniciar-sistema.ps1"
            } else {
                Write-Host "Ejecutar manualmente: mvn spring-boot:run" -ForegroundColor Yellow
            }
        }
    }
    
    default {
        Write-Host "Opción no válida" -ForegroundColor Red
    }
}

Write-Host "`n=== VERIFICACIÓN FINAL ===" -ForegroundColor Green
Write-Host "Para verificar que todo esté correcto:" -ForegroundColor White
Write-Host "1. Iniciar el sistema: .\iniciar-sistema.ps1" -ForegroundColor Cyan
Write-Host "2. Probar funcionalidades principales" -ForegroundColor Cyan
Write-Host "3. Revisar logs en: backend\logs\salud-digital.log" -ForegroundColor Cyan

Write-Host "`n¡Migración incremental completada! 🎉" -ForegroundColor Green
