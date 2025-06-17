# Sistema de Salud Digital

## 🏥 Descripción

Sistema integral de gestión hospitalaria desarrollado con tecnologías modernas que permite la administración completa de pacientes, médicos, citas, medicamentos y historial médico. Diseñado para optimizar los procesos administrativos y médicos en centros de salud.

## 🚀 Características Principales

### 📊 Módulos del Sistema
- **Gestión de Pacientes**: Registro, actualización y consulta de información de pacientes
- **Gestión de Médicos**: Administración de profesionales médicos y sus especialidades
- **Gestión de Citas**: Programación y seguimiento de citas médicas
- **Gestión de Medicamentos**: Control de inventario y prescripciones
- **Historial Médico**: Registro completo del historial clínico de pacientes
- **Resultados de Laboratorio**: Gestión de análisis y estudios médicos
- **Sistema de Usuarios**: Autenticación y autorización con roles

### 🔐 Seguridad
- Autenticación JWT (JSON Web Tokens)
- Control de acceso basado en roles
- Configuración CORS para comunicación segura frontend-backend
- Validación de datos en múltiples capas

### 📱 Interfaz de Usuario
- Interfaz web responsive y moderna
- Dashboard intuitivo con métricas en tiempo real
- Formularios optimizados para gestión de datos
- Sistema de notificaciones y alertas

## 🛠️ Tecnologías Utilizadas

### Backend
- **Java 17**: Lenguaje de programación principal
- **Spring Boot 3.2.5**: Framework principal para desarrollo web
- **Spring Security**: Seguridad y autenticación
- **Spring Data JPA**: Persistencia de datos
- **PostgreSQL**: Base de datos relacional
- **Maven**: Gestión de dependencias
- **JWT**: Tokens de autenticación
- **OpenAPI/Swagger**: Documentación de API
- **Spring Boot Actuator**: Monitoreo y métricas

### Frontend
- **HTML5**: Estructura de páginas web
- **CSS3**: Estilos y diseño responsive
- **JavaScript (ES6+)**: Lógica del cliente
- **Node.js**: Servidor de desarrollo
- **Iconos personalizados**: Sistema de iconografía propio

### Herramientas de Desarrollo
- **PowerShell**: Scripts de automatización
- **Logging**: Sistema de logs con rotación automática
- **Hot Reload**: Desarrollo con recarga automática

## 📁 Estructura del Proyecto

```
Sistema de Salud Digital/
├── backend/                          # Aplicación Spring Boot
│   ├── src/main/java/com/clinica/salud/
│   │   ├── BackendApplication.java    # Clase principal
│   │   ├── config/                    # Configuraciones
│   │   │   ├── CorsConfig.java
│   │   │   ├── SecurityConfig.java
│   │   │   └── OpenApiConfig.java
│   │   ├── controller/                # Controladores REST
│   │   │   ├── AuthController.java
│   │   │   ├── PacienteController.java
│   │   │   ├── MedicoController.java
│   │   │   ├── CitaController.java
│   │   │   ├── MedicamentoController.java
│   │   │   ├── HistorialMedicoController.java
│   │   │   ├── ResultadoLaboratorioController.java
│   │   │   ├── UsuarioController.java
│   │   │   └── SaludController.java
│   │   ├── dto/                       # Data Transfer Objects
│   │   ├── entity/                    # Entidades JPA
│   │   ├── repository/                # Repositorios de datos
│   │   ├── service/                   # Lógica de negocio
│   │   ├── security/                  # Configuración de seguridad
│   │   ├── validation/                # Validaciones personalizadas
│   │   └── util/                      # Utilidades
│   ├── src/main/resources/
│   │   ├── application.yml            # Configuración principal
│   │   └── db.properties             # Configuración de BD
│   ├── logs/                         # Archivos de log
│   └── pom.xml                       # Configuración Maven
├── frontend/                         # Aplicación web
│   ├── login.html                    # Página de inicio de sesión
│   ├── dashboard.html                # Panel principal
│   ├── pacientes-optimized.html      # Gestión de pacientes
│   ├── medicos-optimized.html        # Gestión de médicos
│   ├── citas-optimized.html          # Gestión de citas
│   ├── medicamentos-optimized.html   # Gestión de medicamentos
│   ├── css/                          # Estilos CSS
│   │   ├── styles.css
│   │   └── icons.css
│   ├── js/                           # Scripts JavaScript
│   │   ├── crud-manager.js
│   │   ├── crud-configurations.js
│   │   ├── utils.js
│   │   └── icons.js
│   └── locales/                      # Internacionalización
├── server-frontend.js                # Servidor de desarrollo frontend
├── iniciar-sistema.ps1              # Script de inicio
├── detener-sistema.ps1              # Script de parada
└── inicializar-db.ps1               # Script de inicialización de BD
```

## 🚀 Instalación y Configuración

### Prerequisitos
- Java 17 o superior
- Maven 3.6+
- PostgreSQL 12+
- Node.js 16+ (para servidor de desarrollo)
- PowerShell (Windows)

### 1. Configuración de Base de Datos
```sql
-- Crear base de datos
CREATE DATABASE salud_digital;

-- Crear usuario (opcional)
CREATE USER salud_user WITH ENCRYPTED PASSWORD 'salud_password';
GRANT ALL PRIVILEGES ON DATABASE salud_digital TO salud_user;
```

### 2. Configuración de Variables de Entorno
```bash
# JWT Secret (opcional, tiene valor por defecto)
set JWT_SECRET=tu_clave_secreta_jwt_muy_larga_y_segura

# Configuración de base de datos (si difiere de la configuración por defecto)
set DB_URL=jdbc:postgresql://localhost:5432/salud_digital
set DB_USERNAME=tu_usuario
set DB_PASSWORD=tu_password
```

### 3. Instalación de Dependencias

#### Backend
```bash
cd backend
mvn clean install
```

#### Frontend
```bash
cd frontend
# No requiere instalación adicional, usa Node.js nativo
```

## 🎯 Uso del Sistema

### Inicio Rápido
```powershell
# Desde el directorio raíz del proyecto
.\iniciar-sistema.ps1
```

Este script automáticamente:
- Inicia el backend en el puerto 8080
- Inicia el frontend en el puerto 8081
- Abre el navegador en la aplicación

### Inicio Manual

#### Backend
```bash
cd backend
mvn spring-boot:run
```

#### Frontend
```bash
node server-frontend.js
```

### Detener el Sistema
```powershell
.\detener-sistema.ps1
```

## 🌐 Endpoints de la API

### Autenticación
- `POST /api/auth/login` - Inicio de sesión
- `POST /api/auth/refresh` - Renovar token

### Gestión de Entidades
- `GET|POST|PUT|DELETE /api/pacientes` - Gestión de pacientes
- `GET|POST|PUT|DELETE /api/medicos` - Gestión de médicos
- `GET|POST|PUT|DELETE /api/citas` - Gestión de citas
- `GET|POST|PUT|DELETE /api/medicamentos` - Gestión de medicamentos
- `GET|POST|PUT|DELETE /api/historiales` - Historial médico
- `GET|POST|PUT|DELETE /api/resultados-laboratorio` - Resultados de laboratorio
- `GET|POST|PUT|DELETE /api/usuarios` - Gestión de usuarios

### Monitoreo
- `GET /api/salud` - Estado del sistema
- `GET /api/actuator/health` - Health checks detallados
- `GET /api/actuator/info` - Información de la aplicación

### Documentación API
- `GET /api/swagger-ui.html` - Interfaz Swagger UI
- `GET /api/v3/api-docs` - Documentación OpenAPI

## 📊 Funcionalidades Detalladas

### 👤 Gestión de Pacientes
- Registro completo de datos personales y médicos
- Historial clínico completo
- Búsqueda y filtrado avanzado
- Exportación de informes

### 👨‍⚕️ Gestión de Médicos
- Registro de profesionales médicos
- Gestión de especialidades
- Horarios y disponibilidad
- Estadísticas de consultas

### 📅 Sistema de Citas
- Programación inteligente de citas
- Gestión de disponibilidad médica
- Notificaciones y recordatorios
- Reprogramación y cancelaciones

### 💊 Control de Medicamentos
- Inventario en tiempo real
- Control de prescripciones
- Seguimiento de tratamientos
- Alertas de stock bajo

### 📋 Historial Médico
- Registro completo de consultas
- Seguimiento de tratamientos
- Análisis de evolución clínica
- Generación de reportes médicos

## 🔧 Configuración Avanzada

### Base de Datos
El sistema utiliza PostgreSQL como base de datos principal. La configuración se encuentra en:
- `backend/src/main/resources/application.yml`
- `backend/src/main/resources/db.properties`

### Logging
El sistema incluye logging completo con rotación automática:
- Archivos de log en `backend/logs/`
- Configuración en `application.yml`
- Niveles de log configurables por paquete

### Seguridad
- JWT con expiración configurable (por defecto 24 horas)
- CORS configurado para desarrollo y producción
- Validación de datos en múltiples capas
- Encriptación de contraseñas con BCrypt

## 🧪 Testing

### Ejecutar Tests
```bash
cd backend
mvn test
```

### Ejecutar Tests con Cobertura
```bash
cd backend
mvn clean test jacoco:report
```

## 📈 Monitoreo y Logging

### Logs del Sistema
Los logs se encuentran en `backend/logs/` con rotación automática:
- `salud-digital.log` - Log actual
- `salud-digital.log.YYYY-MM-DD.X.gz` - Logs rotados y comprimidos

### Métricas de Aplicación
- Spring Boot Actuator habilitado
- Health checks automáticos
- Métricas de rendimiento
- Información de la aplicación

## 🔒 Seguridad y Mejores Prácticas

### Autenticación y Autorización
- JWT tokens con expiración configurable
- Roles y permisos granulares
- Protección CSRF deshabilitada para API REST
- CORS configurado apropiadamente

### Validación de Datos
- Validación en el frontend (JavaScript)
- Validación en el backend (Bean Validation)
- Sanitización de inputs
- Manejo de errores robusto

### Configuración de Producción
- Variables de entorno para secretos
- Configuración de perfiles (dev, prod)
- SSL/TLS recomendado para producción
- Backup automático de base de datos recomendado

## 🤝 Contribución

### Estructura de Desarrollo
1. Fork del repositorio
2. Crear rama feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit de cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crear Pull Request

### Estándares de Código
- Java: Seguir convenciones de Oracle
- JavaScript: ES6+ estándar
- CSS: Metodología BEM recomendada
- Comentarios en español para documentación

## 📝 Licencia

Este proyecto es de código propietario desarrollado para uso interno en centros de salud.

## 📞 Soporte

Para soporte técnico o consultas:
- Revisar logs del sistema en `backend/logs/`
- Verificar configuración de base de datos
- Comprobar conectividad de red
- Validar permisos de usuario

## 🗺️ Roadmap

### Funcionalidades Futuras
- [ ] Sistema de notificaciones push
- [ ] Integración con dispositivos médicos
- [ ] Reportes avanzados con gráficos
- [ ] API móvil para aplicación nativa
- [ ] Sistema de backup automático
- [ ] Integración con sistemas de laboratorio externos
- [ ] Telemedicina y consultas virtuales
- [ ] Sistema de facturación integrado

### Mejoras Técnicas
- [ ] Migración a microservicios
- [ ] Implementación de cache Redis
- [ ] Containerización con Docker
- [ ] CI/CD pipeline
- [ ] Tests de integración automatizados
- [ ] Monitoreo con Prometheus/Grafana

## 📊 Estado del Proyecto

- **Versión Actual**: 1.0-SNAPSHOT
- **Estado**: En desarrollo activo
- **Última Actualización**: Junio 2025
- **Cobertura de Tests**: En desarrollo
- **Documentación**: Completa

---

**Sistema de Salud Digital** - Desarrollado con ♥️ para mejorar la atención médica y optimizar la gestión hospitalaria.
