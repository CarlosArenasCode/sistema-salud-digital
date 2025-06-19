# 🚀 GUÍA RÁPIDA DE MIGRACIÓN
## Cómo ejecutar el Sistema de Salud Digital en otra computadora

### ⚡ Opción 1: Instalación Automática (Recomendada)

1. **Copiar archivos del proyecto** a la nueva computadora
2. **Abrir PowerShell como Administrador**
3. **Navegar a la carpeta del proyecto**
4. **Ejecutar el instalador:**
   ```powershell
   .\instalar-sistema.ps1
   ```
5. **¡Listo!** El sistema se configurará automáticamente

### ⚙️ Opción 2: Instalación Manual

#### Paso 1: Instalar Software Requerido
- ✅ **Java 17+**: https://adoptium.net/temurin/releases/
- ✅ **Maven 3.6+**: https://maven.apache.org/download.cgi
- ✅ **PostgreSQL 12+**: https://www.postgresql.org/download/

#### Paso 2: Configurar Base de Datos
```powershell
# Crear base de datos
psql -U postgres -c "CREATE DATABASE sistema_salud;"

# O ejecutar script automático
.\inicializar-db.ps1
```

#### Paso 3: Configurar Credenciales
Editar `config.env` con tus credenciales:
```
DB_PASSWORD=tu_contraseña_aqui
```

#### Paso 4: Ejecutar Sistema
```powershell
.\iniciar-sistema.ps1
```

### 🔧 Verificación Rápida

**Comandos para verificar instalación:**
```powershell
# Verificar Java
java -version

# Verificar Maven
mvn -version

# Verificar PostgreSQL
pg_isready -h localhost -p 5432

# Verificar puertos libres
netstat -ano | findstr :8080
```

### 🌐 Acceso al Sistema

Una vez iniciado:
- **URL Principal**: http://localhost:8080
- **Login**: http://localhost:8080/login.html
- **Dashboard**: http://localhost:8080/dashboard.html

### 🛠️ Solución de Problemas

| Error | Solución |
|-------|----------|
| Java no encontrado | Instalar Java 17+ y verificar PATH |
| Maven no encontrado | Instalar Maven y verificar PATH |
| Puerto 8080 ocupado | Ejecutar `netstat -ano \| findstr :8080` y terminar proceso |
| Error de BD | Verificar PostgreSQL ejecutándose y credenciales |
| Compilación falla | Ejecutar `mvn clean install` en carpeta backend |

### 📁 Archivos Importantes

```
├── instalar-sistema.ps1      # ← Instalador automático
├── iniciar-sistema.ps1       # ← Iniciar aplicación
├── detener-sistema.ps1       # ← Detener aplicación
├── config.env                # ← Configuración personalizable
├── README.md                 # ← Documentación completa
└── backend/
    ├── pom.xml              # ← Dependencias Maven
    └── src/main/resources/
        └── application.yml  # ← Configuración Spring Boot
```

### 🚨 Checklist Pre-Migración

- [ ] Copiar **toda la carpeta del proyecto**
- [ ] Instalar **Java 17+**
- [ ] Instalar **Maven 3.6+**
- [ ] Instalar **PostgreSQL**
- [ ] Configurar usuario **postgres** con contraseña
- [ ] Abrir **PowerShell como Administrador**
- [ ] Ejecutar **`.\instalar-sistema.ps1`**

### 📞 Contacto de Soporte

Si encuentras problemas:
1. Revisar logs en `backend/logs/salud-digital.log`
2. Verificar que todos los servicios estén ejecutándose
3. Consultar la documentación completa en `README.md`

---

**¡Con estos pasos tendrás el sistema funcionando en minutos! 🎉**
