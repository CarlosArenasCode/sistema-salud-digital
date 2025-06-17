// Sistema de Iconos en Español para Sistema de Salud Digital
// Mapeo de iconos SVG con nombres descriptivos en español

class IconosEspanol {
    // Mapeo de nombres en español a archivos SVG
    static iconos = {
        // Iconos principales del sistema
        'latidos-corazon': 'heartbeat_heart_rate_heart_medical_icon_187013.svg',
        'hospital': 'hospital_icon_140108.svg',
        'usuario-hospital': 'hospital_user_icon_198537.svg',
        'doctor': 'avatar_doctor_health_hospital_medical_icon_140085.svg',
        'paciente-herido': 'user_injured_icon_198640.svg',
        'perfil-usuario': '4092564-about-mobile-ui-profile-ui-user-website_114033.svg',
        
        // Iconos de acciones
        'agregar': 'add_circle_create_expand_new_plus_icon_123218.svg',
        'editar': 'edit_modify_icon_196940.svg',
        'cancelar': 'ic-cancel_97589.svg',
        'cerrar-sesion': '4115235-exit-logout-sign-out_114030.svg',
        'descargar': 'download_111133.svg',
        'actualizar': 'twocirclingarrows1_120592.svg',
        'buscar': 'magnifier_118394.svg',
        
        // Iconos de estado y notificación
        'verificado': 'check_circle_icon_128923.svg',
        'advertencia': 'exclamation_triangle_icon_185045.svg',
        'informacion': 'information_105113.svg',
        
        // Iconos específicos del sistema médico
        'medicamentos': 'medicines_icon-icons.com_58863.svg',
        'calendario': 'calendar-with-a-clock-time-tools_icon-icons.com_56831.svg',
        'red-social': 'seo-social-web-network-internet_262_icon-icons.com_61518.svg'
    };

    // Método para crear un elemento img con el icono SVG
    static crear(nombreIcono, clases = '', tamaño = '24') {
        const archivoSvg = this.iconos[nombreIcono];
        if (!archivoSvg) {
            console.warn(`Icono '${nombreIcono}' no encontrado`);
            return `<span class="icono-faltante">[${nombreIcono}]</span>`;
        }

        const rutaIcono = `Iconos/${archivoSvg}`;
        return `<img src="${rutaIcono}" alt="${nombreIcono}" class="icono ${clases}" width="${tamaño}" height="${tamaño}" />`;
    }

    // Método para obtener la ruta del archivo SVG
    static obtenerRuta(nombreIcono) {
        const archivoSvg = this.iconos[nombreIcono];
        if (!archivoSvg) {
            console.warn(`Icono '${nombreIcono}' no encontrado`);
            return '';
        }
        return `Iconos/${archivoSvg}`;
    }

    // Método para crear un icono con clases de Bootstrap
    static crearConBootstrap(nombreIcono, clases = 'me-2', tamaño = '24') {
        return this.crear(nombreIcono, clases, tamaño);
    }

    // Método para reemplazar elementos con data-icono
    static inicializar() {
        document.addEventListener('DOMContentLoaded', () => {
            // Buscar todos los elementos con atributo data-icono
            document.querySelectorAll('[data-icono]').forEach(elemento => {
                const nombreIcono = elemento.getAttribute('data-icono');
                const tamaño = elemento.getAttribute('data-tamaño') || '24';
                const clases = elemento.getAttribute('data-clases') || '';
                
                if (nombreIcono && this.iconos[nombreIcono]) {
                    const rutaIcono = this.obtenerRuta(nombreIcono);
                    elemento.innerHTML = `<img src="${rutaIcono}" alt="${nombreIcono}" class="icono ${clases}" width="${tamaño}" height="${tamaño}" />`;
                }
            });
        });
    }

    // Método para obtener lista de iconos disponibles
    static listarIconos() {
        return Object.keys(this.iconos);
    }
}

// Función de utilidad para crear iconos fácilmente
function icono(nombre, clases = '', tamaño = '24') {
    return IconosEspanol.crear(nombre, clases, tamaño);
}

// Auto-inicializar si está en el navegador
if (typeof window !== 'undefined') {
    IconosEspanol.inicializar();
}

// Exportar para uso en módulos
if (typeof module !== 'undefined' && module.exports) {
    module.exports = IconosEspanol;
}
