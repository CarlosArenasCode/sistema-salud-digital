// Utilidades comunes para el frontend
class AppUtils {
    
    static API_BASE = 'http://localhost:8080/api';
    
    // Realizar petición HTTP genérica
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
    
    // Operaciones CRUD genéricas
    static async getAll(entity) {
        return await this.apiRequest(`/${entity}`);
    }
    
    static async getById(entity, id) {
        return await this.apiRequest(`/${entity}/${id}`);
    }
    
    static async create(entity, data) {
        return await this.apiRequest(`/${entity}`, 'POST', data);
    }
    
    static async update(entity, id, data) {
        return await this.apiRequest(`/${entity}/${id}`, 'PUT', data);
    }
    
    static async delete(entity, id) {
        return await this.apiRequest(`/${entity}/${id}`, 'DELETE');
    }
    
    // Mostrar/ocultar modal genérico
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
    
    // Exportar datos a CSV genérico
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
    
    // Filtrar tabla genérica
    static filterTable(tableBodyId, searchTerm, filterFunction) {
        const tbody = document.getElementById(tableBodyId);
        const rows = tbody.querySelectorAll('tr');
        
        rows.forEach(row => {
            const matches = filterFunction(row, searchTerm);
            row.style.display = matches ? '' : 'none';
        });
    }
    
    // Mostrar mensajes de éxito/error
    static showMessage(message, type = 'info') {
        alert(message); // Puedes reemplazar con una librería de notificaciones
    }
}
