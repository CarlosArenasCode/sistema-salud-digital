# Sistema de Salud Digital - Dockerizado

Este proyecto ahora está completamente dockerizado para facilitar el desarrollo y despliegue en múltiples equipos.

## 🚀 Inicio Rápido

### Prerrequisitos
- [Docker Desktop](https://www.docker.com/products/docker-desktop) instalado
- Git para clonar el repositorio

### Iniciar el sistema
```bash
# En Windows PowerShell
.\iniciar-docker.ps1

# En otros sistemas (Linux/Mac)
docker-compose up --build -d
```

### Detener el sistema
```bash
# En Windows PowerShell
.\detener-docker.ps1

# En otros sistemas
docker-compose down
```

## 🏗️ Arquitectura Docker

El sistema está compuesto por 3 servicios:

1. **database**: PostgreSQL 15
   - Puerto: 5432
   - Base de datos: `sistema_salud`
   - Usuario: `admin` / Contraseña: `admin123`

2. **backend**: Spring Boot (Java 17)
   - Puerto: 8080
   - API REST: http://localhost:8080/api

3. **frontend**: Node.js servidor estático
   - Puerto: 8081
   - Aplicación web: http://localhost:8081

## 🔧 Desarrollo

### Trabajar en otro equipo
1. Clonar el repositorio
2. Instalar Docker Desktop
3. Ejecutar: `.\iniciar-docker.ps1`
4. ¡Listo! El sistema funcionará igual que en tu equipo original

### Ver logs
```bash
# Todos los servicios
docker-compose logs -f

# Solo un servicio específico
docker-compose logs -f backend
docker-compose logs -f frontend
docker-compose logs -f database
```

### Acceder a la base de datos
```bash
# Conectar directamente al contenedor
docker-compose exec database psql -U admin -d sistema_salud
```

### Reconstruir servicios
```bash
# Reconstruir todo
docker-compose up --build

# Reconstruir solo un servicio
docker-compose up --build backend
```

## 📁 Estructura de archivos Docker

- `Dockerfile.backend`: Imagen del backend Spring Boot
- `Dockerfile.frontend`: Imagen del frontend Node.js  
- `docker-compose.yml`: Orquestación completa del sistema
- `iniciar-docker.ps1`: Script de inicio automático
- `detener-docker.ps1`: Script para detener servicios
- `.dockerignore`: Archivos excluidos de las imágenes

## 🆚 Comparación: Local vs Docker

### Método Original (Local)
- ✅ Desarrollo rápido
- ❌ Requiere Java 17, PostgreSQL, Node.js instalados
- ❌ Configuración manual en cada equipo
- ❌ Posibles conflictos de versiones
- ❌ Scripts específicos para Windows

### Método Docker (Recomendado)
- ✅ Funciona en cualquier sistema operativo
- ✅ Solo requiere Docker
- ✅ Configuración automática
- ✅ Versiones consistentes
- ✅ Fácil de compartir y desplegar
- ✅ Aislamiento completo

## 🚀 Siguientes pasos

1. **Instala Docker Desktop** en tu otro equipo
2. **Clona el repositorio** 
3. **Ejecuta**: `.\iniciar-docker.ps1`
4. **Continúa desarrollando** con la certeza de que funcionará igual

¡El proyecto ahora es completamente portable y fácil de usar en cualquier equipo!
