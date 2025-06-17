# Documentación de Iconos en Español
## Sistema de Salud Digital

Este documento describe los iconos disponibles en el sistema con sus nombres en español y archivos correspondientes.

## Iconos Principales del Sistema

| Nombre en Español | Archivo SVG | Descripción |
|-------------------|-------------|-------------|
| `latidos-corazon` | `heartbeat_heart_rate_heart_medical_icon_187013.svg` | Icono de latidos del corazón para el sistema médico |
| `hospital` | `hospital_icon_140108.svg` | Icono de hospital |
| `usuario-hospital` | `hospital_user_icon_198537.svg` | Usuario asociado al hospital |
| `doctor` | `avatar_doctor_health_hospital_medical_icon_140085.svg` | Icono de doctor/médico |
| `paciente-herido` | `user_injured_icon_198640.svg` | Paciente herido/lesionado |
| `perfil-usuario` | `4092564-about-mobile-ui-profile-ui-user-website_114033.svg` | Perfil de usuario |

## Iconos de Acciones

| Nombre en Español | Archivo SVG | Descripción |
|-------------------|-------------|-------------|
| `agregar` | `add_circle_create_expand_new_plus_icon_123218.svg` | Botón para agregar/crear nuevo |
| `editar` | `edit_modify_icon_196940.svg` | Icono de edición |
| `cancelar` | `ic-cancel_97589.svg` | Cancelar acción |
| `cerrar-sesion` | `4115235-exit-logout-sign-out_114030.svg` | Cerrar sesión |
| `descargar` | `download_111133.svg` | Descargar archivo |
| `actualizar` | `twocirclingarrows1_120592.svg` | Actualizar/refrescar |
| `buscar` | `magnifier_118394.svg` | Buscar/lupa |
| `ver` | `information_105113.svg` | Ver información/detalles |

## Iconos de Estado y Notificación

| Nombre en Español | Archivo SVG | Descripción |
|-------------------|-------------|-------------|
| `verificado` | `check_circle_icon_128923.svg` | Verificado/completado |
| `advertencia` | `exclamation_triangle_icon_185045.svg` | Advertencia/alerta |
| `informacion` | `information_105113.svg` | Información |

## Iconos Específicos del Sistema Médico

| Nombre en Español | Archivo SVG | Descripción |
|-------------------|-------------|-------------|
| `medicamentos` | `medicines_icon-icons.com_58863.svg` | Medicamentos/pastillas |
| `calendario` | `calendar-with-a-clock-time-tools_icon-icons.com_56831.svg` | Calendario con reloj |
| `red-social` | `seo-social-web-network-internet_262_icon-icons.com_61518.svg` | Red social/conectividad |

## Uso en HTML

### Método directo con etiqueta img:
```html
<img src="Iconos/heartbeat_heart_rate_heart_medical_icon_187013.svg" 
     alt="Latidos corazón" 
     class="icono me-2" 
     width="24" 
     height="24">
```

### Método con JavaScript (sistema de iconos):
```html
<!-- Agregar el script -->
<script src="js/iconos-espanol.js"></script>

<!-- Usar con atributos data -->
<span data-icono="latidos-corazon" data-tamaño="24" data-clases="me-2"></span>

<!-- O con JavaScript -->
<script>
document.getElementById('mi-elemento').innerHTML = IconosEspanol.crear('latidos-corazon', 'me-2', '24');
</script>
```

## Clases CSS Disponibles

### Tamaños:
- `.icono-xs` - 12px
- `.icono-sm` - 16px  
- `.icono-md` - 24px (por defecto)
- `.icono-lg` - 32px
- `.icono-xl` - 48px

### Espaciado Bootstrap:
- `.me-1, .me-2, .me-3` - Margen derecho
- `.ms-1, .ms-2, .ms-3` - Margen izquierdo
- `.mb-1, .mb-2, .mb-3` - Margen inferior

### Especiales:
- `.modulo-icono` - Para iconos grandes en módulos del dashboard
- `.tabla-acciones` - Para iconos en tablas
- `.info-usuario` - Para información de usuario

## Ejemplos de Implementación

### En el Dashboard:
```html
<h1 class="text-primary mb-3">
    <img src="Iconos/hospital_icon_140108.svg" alt="Hospital" class="icono me-2" width="32" height="32">
    Bienvenido al Sistema de Salud Digital
</h1>
```

### En Botones:
```html
<button class="btn btn-primary">
    <img src="Iconos/add_circle_create_expand_new_plus_icon_123218.svg" alt="Agregar" class="icono me-1" width="16" height="16">
    Nuevo Paciente
</button>
```

### En Navegación:
```html
<a class="navbar-brand" href="dashboard.html">
    <img src="Iconos/heartbeat_heart_rate_heart_medical_icon_187013.svg" alt="Sistema de Salud" class="icono me-2" width="24" height="24">
    Sistema de Salud Digital
</a>
```

### En Botones de Acción de Tabla:
```html
<!-- Botón Ver -->
<button class="btn btn-view btn-sm" onclick="view(id)">
    <img src="Iconos/information_105113.svg" alt="Ver" class="icono" width="14" height="14">
</button>

<!-- Botón Editar -->
<button class="btn btn-edit btn-sm" onclick="edit(id)">
    <img src="Iconos/edit_modify_icon_196940.svg" alt="Editar" class="icono" width="14" height="14">
</button>

<!-- Botón Eliminar -->
<button class="btn btn-delete btn-sm" onclick="delete(id)">
    <img src="Iconos/ic-cancel_97589.svg" alt="Eliminar" class="icono" width="14" height="14">
</button>
```

### En Campos de Búsqueda:
```html
<div class="input-group">
    <span class="input-group-text">
        <img src="Iconos/magnifier_118394.svg" alt="Buscar" class="icono" width="16" height="16">
    </span>
    <input type="text" class="form-control" placeholder="Buscar...">
</div>
```

### En Botones de Utilidades:
```html
<!-- Recargar -->
<button class="btn btn-secondary me-2" onclick="reload()">
    <img src="Iconos/twocirclingarrows1_120592.svg" alt="Recargar" class="icono me-1" width="16" height="16">
    Recargar
</button>

<!-- Exportar -->
<button class="btn btn-secondary me-2" onclick="export()">
    <img src="Iconos/download_111133.svg" alt="Exportar" class="icono me-1" width="16" height="16">
    Exportar
</button>
```
