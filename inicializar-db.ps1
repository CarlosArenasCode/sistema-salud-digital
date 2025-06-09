# Script para inicializar la base de datos PostgreSQL 'sistema_salud' si no existe
# Requiere que psql esté en el PATH y el usuario 'postgres' tenga permisos

$database = "sistema_salud"
$usuario = "postgres"
$contrasena = "1234"
$dbHost = "localhost"
$puerto = "5432"

# Verificar si la base de datos existe
$checkDbCmd = "psql -U $usuario -h $dbHost -p $puerto -lqt | Select-String $database"
$dbExists = Invoke-Expression $checkDbCmd

if (-not $dbExists) {
    Write-Host "La base de datos '$database' no existe. Creando..."
    $env:PGPASSWORD = $contrasena
    psql -U $usuario -h $dbHost -p $puerto -c "CREATE DATABASE $database;"
    Write-Host "Base de datos '$database' creada."
    # Ejecutar el script de esquema si existe
    $schemaPath = "c:\WorkSpace\Sistema de Salud Digital\backend\src\main\resources\schema-postgresql.sql"
    if (Test-Path $schemaPath) {
        Write-Host "Inicializando esquema desde schema-postgresql.sql..."
        psql -U $usuario -h $dbHost -p $puerto -d $database -f $schemaPath
        Write-Host "Esquema inicializado."
    }
    else {
        Write-Host "No se encontró el archivo de esquema: $schemaPath"
    }
}
else {
    Write-Host "La base de datos '$database' ya existe."
}
