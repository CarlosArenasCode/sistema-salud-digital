// Utilidades comunes para el frontend
class AppUtils {
    
    // ===================================================
    // CONFIGURACIÓN DE API Y PETICIONES HTTP
    // ===================================================
    static API_BASE = 'http://localhost:8080/api';
    
    /**
     * Realiza peticiones HTTP genéricas a la API
     */
    static async apiRequest(endpoint, method = 'GET', data = null) {
        const config = {
            method,
            headers: {
                'Content-Type': 'application/json'
            }
        };
        
        if (data) {
            config.body = JSON.stringify(data);
        }
        
        try {
            const response = await fetch(`${this.API_BASE}${endpoint}`, config);
            return response;
        } catch (error) {
            console.error('Error en petición:', error);
            throw error;
        }
    }
    
    // ===================================================
    // OPERACIONES CRUD GENÉRICAS
    // ===================================================
    static async getAll(entity) {
        const response = await this.apiRequest(`/${entity}`);
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error(`Error fetching data: ${response.status}`);
        }
    }
    
    static async get(endpoint) {
        const response = await this.apiRequest(endpoint);
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error(`Error fetching data: ${response.status}`);
        }
    }
    
    static async getById(entity, id) {
        const response = await this.apiRequest(`/${entity}/${id}`);
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error(`Error fetching data: ${response.status}`);
        }
    }
    
    static async create(entity, data) {
        const response = await this.apiRequest(`/${entity}`, 'POST', data);
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error(`Error creating data: ${response.status}`);
        }
    }
    
    static async update(entity, id, data) {
        const response = await this.apiRequest(`/${entity}/${id}`, 'PUT', data);
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error(`Error updating data: ${response.status}`);
        }
    }
    
    static async delete(entity, id) {
        const response = await this.apiRequest(`/${entity}/${id}`, 'DELETE');
        if (response.ok) {
            return response.status === 204 ? null : await response.json();
        } else {
            throw new Error(`Error deleting data: ${response.status}`);
        }
    }
    
    // ===================================================
    // MANIPULACIÓN DE INTERFAZ DE USUARIO
    // ===================================================
    
    /**
     * Funciones para mostrar y ocultar modales de Bootstrap
     */
    static showModal(modalId) {
        const modalEl = document.getElementById(modalId);
        const modal = bootstrap.Modal.getOrCreateInstance(modalEl);
        modal.show();
    }

    static hideModal(modalId) {
        const modalEl = document.getElementById(modalId);
        const modal = bootstrap.Modal.getOrCreateInstance(modalEl);
        modal.hide();
    }
    
    /**
     * Filtra elementos en una tabla según un criterio de búsqueda
     */
    static filterTable(tableBodyId, searchTerm, filterFunction) {
        const tbody = document.getElementById(tableBodyId);
        const rows = tbody.querySelectorAll('tr');
        
        rows.forEach(row => {
            const matches = filterFunction(row, searchTerm);
            row.style.display = matches ? '' : 'none';
        });
    }
    
    // ===================================================
    // EXPORTACIÓN DE DATOS
    // ===================================================
    
    /**
     * Exporta datos a un archivo CSV descargable
     */
    static exportToCSV(data, headers, filename) {
        const csvContent = [
            headers.join(','),
            ...data.map(row => headers.map(header => row[header] || '').join(','))
        ].join('\n');
        
        const blob = new Blob([csvContent], { type: 'text/csv' });
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = filename;
        a.click();
        URL.revokeObjectURL(url);
    }
    
    // ===================================================
    // NOTIFICACIONES Y MENSAJES
    // ===================================================
    
    /**
     * Muestra mensajes de notificación tipo toast
     */
    static showMessage(message, type = 'info') {
        // Crear un toast Bootstrap
        const toastContainer = this.getOrCreateToastContainer();
        
        const toastId = 'toast-' + Date.now();
        const bgClass = {
            'success': 'bg-success',
            'error': 'bg-danger',
            'warning': 'bg-warning',
            'info': 'bg-info'
        }[type] || 'bg-info';
        
        const toastHtml = `
            <div id="${toastId}" class="toast ${bgClass} text-white" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="toast-header ${bgClass} text-white border-0">
                    <strong class="me-auto">Sistema de Salud</strong>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
                <div class="toast-body">
                    ${message}
                </div>
            </div>
        `;
        
        toastContainer.insertAdjacentHTML('beforeend', toastHtml);
        
        const toastElement = document.getElementById(toastId);
        const toast = new bootstrap.Toast(toastElement, { delay: 4000 });
        toast.show();
        
        // Limpiar el toast después de que se cierre
        toastElement.addEventListener('hidden.bs.toast', () => {
            toastElement.remove();
        });
    }
    
    static getOrCreateToastContainer() {
        let container = document.getElementById('toast-container');
        if (!container) {
            container = document.createElement('div');
            container.id = 'toast-container';
            container.className = 'position-fixed top-0 end-0 p-3';
            container.style.zIndex = '1055';
            document.body.appendChild(container);
        }
        return container;
    }

    // ===================================================
    // AUTENTICACIÓN Y MANEJO DE SESIONES
    // ===================================================
    
    /**
     * Verifica si el usuario está autenticado
     */
    static checkAuth() {
        const token = localStorage.getItem('auth_token');
        if (!token) {
            window.location.href = 'login.html';
            return false;
        }
        return true;
    }
    
    /**
     * Cierra la sesión del usuario
     */
    static logout() {
        if (confirm('¿Está seguro que desea cerrar sesión?')) {
            localStorage.removeItem('auth_token');
            localStorage.removeItem('user_data');
            window.location.href = 'login.html';
        }
    }
    
    /**
     * Obtiene los datos del usuario actualmente logueado
     */
    static getUserData() {
        return JSON.parse(localStorage.getItem('user_data') || '{}');
    }
}
