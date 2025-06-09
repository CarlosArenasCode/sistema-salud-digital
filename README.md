# 🏥 Sistema de Salud Digital

Sistema integral de gestión hospitalaria desarrollado con Spring Boot y tecnologías web modernas.

## 📋 Descripción

El **Sistema de Salud Digital** es una aplicación completa para la gestión de centros médicos que incluye:

- 👥 **Gestión de Pacientes**: Registro, consulta y administración de pacientes
- 👨‍⚕️ **Gestión de Médicos**: Control de especialistas y horarios
- 📅 **Sistema de Citas**: Programación y seguimiento de citas médicas
- 📋 **Historiales Médicos**: Registros clínicos detallados
- 💊 **Gestión de Medicamentos**: Inventario y prescripciones
- 💰 **Facturación**: Sistema de cobros y pagos
- 🔐 **Autenticación**: Sistema seguro de login con JWT

## 🛠️ Tecnologías Utilizadas

### Backend
- **Java 17**
- **Spring Boot 3.2.5**
- **Spring Security** (JWT)
- **Spring Data JPA**
- **PostgreSQL** / H2 Database
- **Maven**

### Frontend
- **HTML5/CSS3/JavaScript**
- **Bootstrap** (UI Framework)
- **Node.js** (Servidor estático)

### DevOps
- **Docker & Docker Compose**
- **PowerShell** (Automatización)

## 🚀 Inicio Rápido

### Opción 1: Con Docker (Recomendado)

```bash
# 1. Clona el repositorio
git clone https://github.com/TU_USUARIO/sistema-salud-digital.git
cd sistema-salud-digital

# 2. Inicia el sistema con Docker
.\iniciar-docker.ps1

# 3. Accede a la aplicación
# Frontend: http://localhost:8081
# Backend API: http://localhost:8080/api
```

### Opción 2: Instalación Local

#### Prerrequisitos
- Java 17+
- PostgreSQL 12+
- Node.js 16+
- Maven 3.6+

#### Pasos
```bash
# 1. Clona el repositorio
git clone https://github.com/TU_USUARIO/sistema-salud-digital.git
cd sistema-salud-digital

# 2. Configura la base de datos
.\inicializar-db.ps1

# 3. Inicia el sistema
.\iniciar-sistema.ps1
```

## 📁 Estructura del Proyecto

```
sistema-salud-digital/
├── backend/                    # Spring Boot Application
│   ├── src/main/java/         # Código fuente Java
│   ├── src/main/resources/    # Configuraciones y SQL
│   └── pom.xml               # Dependencias Maven
├── frontend/                  # Aplicación Web
│   ├── *.html                # Páginas HTML
│   ├── styles.css            # Estilos CSS
│   └── js/                   # JavaScript
├── docker-compose.yml        # Orquestación Docker
├── Dockerfile.*              # Imágenes Docker
└── *.ps1                     # Scripts de automatización
```

## 🔧 Configuración

### Base de Datos
El sistema soporta dos perfiles:
- **postgresql**: Producción (por defecto)
- **h2**: Desarrollo rápido

### Variables de Entorno (Docker)
```yaml
SPRING_PROFILES_ACTIVE: postgresql
SPRING_DATASOURCE_URL: jdbc:postgresql://database:5432/sistema_salud
SPRING_DATASOURCE_USERNAME: admin
SPRING_DATASOURCE_PASSWORD: admin123
```

## 🏃‍♂️ Uso del Sistema

### Credenciales por Defecto
- **Usuario**: admin
- **Contraseña**: admin123

### Funcionalidades Principales

1. **Dashboard**: Vista general del sistema
2. **Pacientes**: Gestión completa de pacientes
3. **Médicos**: Administración de personal médico
4. **Citas**: Sistema de programación de citas
5. **Medicamentos**: Control de inventario farmacéutico
6. **Reportes**: Análisis y estadísticas

## 🐳 Docker

### Servicios
- **database**: PostgreSQL 15
- **backend**: Spring Boot API (Puerto 8080)
- **frontend**: Node.js Server (Puerto 8081)

### Comandos Útiles
```bash
# Ver logs
docker-compose logs -f

# Reconstruir servicios
docker-compose up --build

# Detener servicios
.\detener-docker.ps1
```

## 🛠️ Desarrollo

### Compilar Backend
```bash
cd backend
mvn clean compile
mvn spring-boot:run
```

### Iniciar Frontend
```bash
node server-frontend.js
```

### Base de Datos
```bash
# Conectar a PostgreSQL
docker-compose exec database psql -U admin -d sistema_salud
```

## 📚 API Documentation

Una vez iniciado el backend, la documentación de la API está disponible en:
- **Swagger UI**: http://localhost:8080/api/swagger-ui.html
- **OpenAPI**: http://localhost:8080/api/v3/api-docs

### Endpoints Principales
- `POST /api/auth/login` - Autenticación
- `GET /api/pacientes` - Listar pacientes
- `POST /api/citas` - Crear cita
- `GET /api/medicos` - Listar médicos

## 🤝 Contribuir

1. **Fork** el proyecto
2. **Crea** una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. **Commit** tus cambios (`git commit -m 'Añadir nueva funcionalidad'`)
4. **Push** a la rama (`git push origin feature/nueva-funcionalidad`)
5. **Abre** un Pull Request

## 📝 Notas de Versión

### v1.0.0
- ✅ Sistema de autenticación JWT
- ✅ CRUD completo para todas las entidades
- ✅ Interfaz web responsive
- ✅ Dockerización completa
- ✅ Base de datos PostgreSQL
- ✅ Sistema de logs

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## 👥 Autores

- **Tu Nombre** - *Desarrollo Principal* - [TuGitHub](https://github.com/TU_USUARIO)

## 🆘 Soporte

Si tienes problemas o preguntas:

1. **Issues**: [GitHub Issues](https://github.com/TU_USUARIO/sistema-salud-digital/issues)
2. **Documentación**: Ver `README-DOCKER.md` para más detalles de Docker
3. **Wiki**: [GitHub Wiki](https://github.com/TU_USUARIO/sistema-salud-digital/wiki)

---

⭐ **¡Si te gusta el proyecto, dale una estrella en GitHub!** ⭐
