// Sistema de Iconos SVG para Sistema de Salud Digital
// Reemplaza Font Awesome con iconos SVG optimizados

class SvgIcons {
    static icons = {
        // Iconos principales del sistema
        heartbeat: `<svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M21 10.12h-6.78l-2.74-2.82c-2.73-2.7-7.15-2.8-9.88-.1-2.73 2.71-2.73 7.08 0 9.79L12 24l10.4-7.01c2.73-2.71 2.73-7.08 0-9.79L21 10.12z" fill="currentColor"/>
        </svg>`,

        userMd: `<svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z" fill="currentColor"/>
            <path d="M15.5 2h-1v2h-2v2h2v2h1V6h2V4h-2V2z" fill="currentColor"/>
        </svg>`,

        users: `<svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M16 4c2.2 0 4 1.8 4 4s-1.8 4-4 4-4-1.8-4-4 1.8-4 4-4zm0 2c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2z" fill="currentColor"/>
            <path d="M8 4c2.2 0 4 1.8 4 4s-1.8 4-4 4-4-1.8-4-4 1.8-4 4-4zm0 2c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2z" fill="currentColor"/>
            <path d="M8 14c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4zm8 0c-.29 0-.62.02-.97.05C16.2 14.6 17 15.7 17 17v1h7v-2c0-2.66-5.33-4-8-4z" fill="currentColor"/>
        </svg>`,

        calendar: `<svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M19 3h-1V1h-2v2H8V1H6v2H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm0 16H5V8h14v11z" fill="currentColor"/>
            <circle cx="12" cy="12" r="2" fill="currentColor"/>
        </svg>`,

        pills: `<svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M4.22 11.29l7.07-7.07c1.39-1.39 3.65-1.39 5.04 0l3.45 3.45c1.39 1.39 1.39 3.65 0 5.04l-7.07 7.07c-1.39 1.39-3.65 1.39-5.04 0l-3.45-3.45c-1.39-1.39-1.39-3.65 0-5.04z" fill="currentColor"/>
            <path d="M9.88 14.12l4.24-4.24-1.41-1.41-4.24 4.24 1.41 1.41z" fill="#ffffff"/>
        </svg>`,

        // Iconos de acciones
        plus: `<svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm5 11h-4v4h-2v-4H7v-2h4V7h2v4h4v2z" fill="currentColor"/>
        </svg>`,

        edit: `<svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z" fill="currentColor"/>
        </svg>`,

        trash: `<svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z" fill="currentColor"/>
        </svg>`,

        // Iconos del sistema
        hospital: `<svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M12 3L2 12h3v8h14v-8h3L12 3zm-1 16H9v-2h2v2zm0-4H9v-2h2v2zm4 4h-2v-2h2v2zm0-4h-2v-2h2v2z" fill="currentColor"/>
            <path d="M11 6h2v2h2v2h-2v2h-2V10H9V8h2V6z" fill="currentColor"/>
        </svg>`,

        signOut: `<svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M17 7l-1.41 1.41L18.17 11H8v2h10.17l-2.58 2.59L17 17l5-5-5-5z" fill="currentColor"/>
            <path d="M2 2v20h10v-2H4V4h8V2H2z" fill="currentColor"/>
        </svg>`,

        // Iconos de estado
        checkCircle: `<svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm-2 15l-5-5 1.41-1.41L10 14.17l7.59-7.59L19 8l-9 9z" fill="currentColor"/>
        </svg>`,

        exclamationTriangle: `<svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M1 21h22L12 2 1 21zm12-3h-2v-2h2v2zm0-4h-2v-4h2v4z" fill="currentColor"/>
        </svg>`,

        infoCircle: `<svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm1 15h-2v-6h2v6zm0-8h-2V7h2v2z" fill="currentColor"/>
        </svg>`,

        // Iconos adicionales
        search: `<svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z" fill="currentColor"/>
        </svg>`,

        download: `<svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M19 9h-4V3H9v6H5l7 7 7-7zM5 18v2h14v-2H5z" fill="currentColor"/>
        </svg>`,

        refresh: `<svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M17.65 6.35C16.2 4.9 14.21 4 12 4c-4.42 0-7.99 3.58-7.99 8s3.57 8 7.99 8c3.73 0 6.84-2.55 7.73-6h-2.08c-.82 2.33-3.04 4-5.65 4-3.31 0-6-2.69-6-6s2.69-6 6-6c1.66 0 3.14.69 4.22 1.78L13 11h7V4l-2.35 2.35z" fill="currentColor"/>
        </svg>`,

        close: `<svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z" fill="currentColor"/>
        </svg>`,

        // Iconos médicos específicos
        userInjured: `<svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z" fill="currentColor"/>
            <path d="M14 8h-4v1h4V8zm0 2h-4v1h4v-1z" fill="#dc3545"/>
        </svg>`,

        hospitalUser: `<svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M12 2l10 9h-3v9H5v-9H2l10-9zm-1 16h2v-2h-2v2zm0-4h2v-2h-2v2zm0-4h2V8h-2v2z" fill="currentColor"/>
            <circle cx="18" cy="6" r="3" fill="currentColor"/>
        </svg>`
    };

    // Método para crear un icono SVG
    static create(iconName, className = '', size = 24) {
        const iconSvg = this.icons[iconName];
        if (!iconSvg) {
            console.warn(`Icono '${iconName}' no encontrado`);
            return `<span class="icon-missing">[${iconName}]</span>`;
        }

        // Modificar el SVG para incluir clases y tamaño personalizado
        return iconSvg
            .replace('width="24"', `width="${size}"`)
            .replace('height="24"', `height="${size}"`)
            .replace('<svg ', `<svg class="svg-icon ${className}" `);
    }

    // Método para obtener solo el contenido interno del SVG
    static getPath(iconName) {
        const iconSvg = this.icons[iconName];
        if (!iconSvg) return '';
        
        // Extraer el contenido entre las etiquetas <svg>
        const match = iconSvg.match(/<svg[^>]*>(.*?)<\/svg>/s);
        return match ? match[1] : '';
    }

    // Método para reemplazar Font Awesome en el DOM
    static replaceFontAwesome() {
        const faMapping = {
            'fa-heartbeat': 'heartbeat',
            'fa-user-md': 'userMd', 
            'fa-users': 'users',
            'fa-calendar-alt': 'calendar',
            'fa-pills': 'pills',
            'fa-plus': 'plus',
            'fa-edit': 'edit',
            'fa-trash': 'trash',
            'fa-hospital': 'hospital',
            'fa-sign-out-alt': 'signOut',
            'fa-check-circle': 'checkCircle',
            'fa-exclamation-triangle': 'exclamationTriangle',
            'fa-info-circle': 'infoCircle',
            'fa-search': 'search',
            'fa-download': 'download',
            'fa-sync': 'refresh',
            'fa-times': 'close',
            'fa-user-injured': 'userInjured',
            'fa-hospital-user': 'hospitalUser'
        };

        // Buscar todos los elementos <i> con clases de Font Awesome
        document.querySelectorAll('i[class*="fa-"]').forEach(element => {
            const classes = element.className.split(' ');
            let iconName = null;
            
            // Buscar la clase del icono
            for (const cls of classes) {
                if (faMapping[cls]) {
                    iconName = faMapping[cls];
                    break;
                }
            }
            
            if (iconName) {
                // Mantener clases que no sean de Font Awesome
                const otherClasses = classes.filter(cls => 
                    !cls.startsWith('fa') && cls !== 'fas' && cls !== 'far' && cls !== 'fab'
                ).join(' ');
                
                // Reemplazar con SVG
                element.outerHTML = SvgIcons.create(iconName, otherClasses);
            }
        });
    }

    // Método para inicializar el sistema de iconos
    static init() {
        // Esperar a que el DOM esté cargado
        if (document.readyState === 'loading') {
            document.addEventListener('DOMContentLoaded', () => {
                this.replaceFontAwesome();
            });
        } else {
            this.replaceFontAwesome();
        }
    }
}

// Función de utilidad para crear iconos fácilmente
function icon(name, className = '', size = 24) {
    return SvgIcons.create(name, className, size);
}

// Auto-inicializar si está en el navegador
if (typeof window !== 'undefined') {
    SvgIcons.init();
}

// Exportar para uso en módulos
if (typeof module !== 'undefined' && module.exports) {
    module.exports = SvgIcons;
}
