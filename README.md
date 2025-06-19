# Sistema de Salud Digital

Sistema web para gestión de clínicas médicas desarrollado con Spring Boot y frontend web estático.

## 📋 Requisitos del Sistema

### Software Necesario
1. **Java 17** o superior (JDK)
2. **Apache Maven 3.6** o superior
3. **PostgreSQL 12** o superior
4. **PowerShell** (Windows) o **Bash** (Linux/macOS)
5. **Git** (para clonar el repositorio)

### Puertos Utilizados
- **8080**: Aplicación web (Backend + Frontend)
- **5432**: Base de datos PostgreSQL

## 🚀 Instalación y Configuración

### 1. Instalar Java 17
```bash
# Verificar si Java está instalado
java -version

# Si no está instalado, descargar desde:
# https://adoptium.net/temurin/releases/
```

### 2. Instalar Maven
```bash
# Verificar si Maven está instalado
mvn -version

# Si no está instalado, descargar desde:
# https://maven.apache.org/download.cgi
```

### 3. Instalar PostgreSQL
```bash
# Descargar e instalar PostgreSQL desde:
# https://www.postgresql.org/download/

# Configurar usuario 'postgres' con contraseña '1234'
# O modificar las credenciales en application.yml
```

### 4. Clonar o Copiar el Proyecto
```bash
# Si usas Git
git clone <url-del-repositorio>

# O copiar toda la carpeta del proyecto a la nueva computadora
```

## 🔧 Configuración de Base de Datos

### Opción 1: Configuración Automática (Recomendada)
```powershell
# En Windows PowerShell
.\inicializar-db.ps1
```

### Opción 2: Configuración Manual
```sql
-- Conectarse a PostgreSQL como usuario postgres
psql -U postgres

-- Crear la base de datos
CREATE DATABASE sistema_salud;

-- Salir de psql
\q
```

### Configurar Credenciales de Base de Datos
Editar el archivo `backend/src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/sistema_salud
    username: postgres
    password: TU_CONTRASEÑA_AQUI  # Cambiar por tu contraseña
```

O establecer variable de entorno:
```powershell
# Windows
$env:DB_PASSWORD = "tu_contraseña"

# Linux/macOS
export DB_PASSWORD="tu_contraseña"
```

## ▶️ Ejecutar el Sistema

### Opción 1: Script Automático (Recomendada)
```powershell
# Windows PowerShell
.\iniciar-sistema.ps1
```

### Opción 2: Ejecución Manual
```bash
# Navegar al directorio backend
cd backend

# Compilar y ejecutar
mvn clean spring-boot:run
```

### ¿Cómo saber que funciona?
1. La aplicación se iniciará en: http://localhost:8080
2. Página de login: http://localhost:8080/login.html
3. El navegador se abrirá automáticamente (con script automático)

## 🛠️ Estructura del Proyecto

```
Sistema de Salud Digital/
├── backend/                    # Aplicación Spring Boot
│   ├── src/main/java/         # Código fuente Java
│   ├── src/main/resources/    # Configuración y archivos estáticos
│   │   ├── static/           # Frontend (HTML, CSS, JS)
│   │   └── application.yml   # Configuración de la aplicación
│   └── pom.xml               # Dependencias Maven
├── frontend-backup/           # Respaldo del frontend
├── iniciar-sistema.ps1       # Script de inicio automático
├── inicializar-db.ps1        # Script de configuración de BD
├── detener-sistema.ps1       # Script para detener el sistema
└── test-sistema-completo.ps1 # Script de pruebas
```

## 🔍 Solución de Problemas Comunes

### Error: Puerto 8080 en uso
```powershell
# Encontrar proceso usando el puerto
netstat -ano | findstr :8080

# Terminar proceso (cambiar PID por el número real)
taskkill /PID <numero_pid> /F
```

### Error: No se puede conectar a PostgreSQL
1. Verificar que PostgreSQL esté ejecutándose:
   ```bash
   # Windows
   services.msc (buscar PostgreSQL)
   
   # O verificar con:
   pg_isready -h localhost -p 5432
   ```

2. Verificar credenciales en `application.yml`
3. Verificar que la base de datos `sistema_salud` exista

### Error: Java no encontrado
```bash
# Verificar instalación
java -version
javac -version

# Verificar variable JAVA_HOME
echo $JAVA_HOME  # Linux/macOS
echo $env:JAVA_HOME  # Windows PowerShell
```

### Error: Maven no encontrado
```bash
# Verificar instalación
mvn -version

# Verificar variable PATH
echo $PATH  # Linux/macOS
echo $env:PATH  # Windows PowerShell
```

## 📱 Funcionalidades del Sistema

- **Gestión de Pacientes**: Registro, edición y consulta
- **Gestión de Médicos**: Administración de personal médico
- **Gestión de Citas**: Programación y seguimiento
- **Gestión de Medicamentos**: Inventario farmacéutico
- **Dashboard**: Panel de control con estadísticas
- **Sistema de Login**: Acceso seguro al sistema

## 🔐 Usuarios de Prueba

El sistema crea automáticamente usuarios de prueba:
- **Admin**: usuario/contraseña para administradores
- **Doctor**: usuario/contraseña para médicos
- **Recepcionista**: usuario/contraseña para recepcionistas

*(Los detalles específicos se pueden encontrar en los logs de inicio)*

## 📝 Notas Adicionales

- El sistema usa **Spring Boot 3.2.5** con **Java 17**
- La base de datos se actualiza automáticamente (DDL auto-update)
- Los archivos estáticos se sirven desde el mismo puerto que la API
- Los logs se guardan en `backend/logs/salud-digital.log`

## 🆘 Soporte

Para problemas específicos:
1. Revisar los logs en `backend/logs/salud-digital.log`
2. Verificar que todos los requisitos estén instalados
3. Confirmar que la base de datos esté funcionando
4. Verificar que los puertos 8080 y 5432 estén libres

---

**¡Sistema listo para usar! 🎉**
