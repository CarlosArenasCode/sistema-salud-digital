// Clase genérica para manejo de formularios CRUD
class CRUDManager {
    constructor(config) {
        this.config = config; // Almacenar toda la configuración
        this.entityName = config.entityName;
        this.entityNamePlural = config.entityNamePlural || config.entityName + 's';
        this.modalId = config.modalId;
        this.formId = config.formId;
        this.tableBodyId = config.tableBodyId;
        this.searchInputId = config.searchInputId;
        this.fields = config.fields; // Array de objetos {name, type, required}
        this.data = [];
        this.currentId = null;
        
        this.init();
    }
    
    init() {
        this.loadData();
        this.bindEvents();
    }
    
    async loadData() {
        try {
            this.data = await AppUtils.getAll(this.entityNamePlural.toLowerCase());
            this.renderTable();
            this.updateCount();
        } catch (error) {
            console.error(`Error cargando ${this.entityNamePlural}:`, error);
            AppUtils.showMessage(`Error cargando ${this.entityNamePlural}: ${error.message}`, 'error');
        }
    }
      renderTable() {
        const tbody = document.getElementById(this.tableBodyId);
        if (tbody) {
            tbody.innerHTML = this.data.map(item => this.renderTableRow(item)).join('');
        }
    }
    
    renderTableRow(item) {
        // Si hay una función personalizada formatTableRow en la configuración, usarla
        if (this.config && typeof this.config.formatTableRow === 'function') {
            return this.config.formatTableRow(item);
        }
        
        // Si no, usar el formato genérico
        const cells = this.fields.map(field => `<td>${item[field.name] || ''}</td>`).join('');
        // Nombre de variable manager basado en entityNamePlural ('pacientes' -> 'pacienteManager')
        const base = this.entityNamePlural.toLowerCase().slice(0, -1);
        const managerVar = `${base}Manager`;
        return `
            <tr>
                ${cells}
                <td class="table-actions">
                    <button class="btn btn-edit btn-sm" data-bs-toggle="modal" data-bs-target="#${this.modalId}" onclick="${managerVar}.edit(${item.id})">✏️</button>
                    <button class="btn btn-delete btn-sm" onclick="${managerVar}.delete(${item.id})">🗑️</button>
                </td>
            </tr>
        `;
    }
    
    openModal(id = null) {
        this.currentId = id;
        const form = document.getElementById(this.formId);
        const title = document.querySelector(`#${this.modalId} .modal-title`);
        
        if (id) {
            // Cargar datos para edición
            const item = this.data.find(d => d.id === id);
            if (item) {
                this.fields.forEach(field => {
                    const element = document.getElementById(field.name);
                    if (element) {
                        if (element.type === 'checkbox') {
                            element.checked = item[field.name] || false;
                        } else {
                            element.value = item[field.name] || '';
                        }
                    }
                });
            }
            if (title) title.textContent = `Editar ${this.entityName}`;
            if (form.querySelector('[type="submit"]')) {
                form.querySelector('[type="submit"]').textContent = 'Actualizar';
            }
        } else {
            // Limpiar formulario para nuevo registro
            form.reset();
            if (title) title.textContent = `Nuevo ${this.entityName}`;
            if (form.querySelector('[type="submit"]')) {
                form.querySelector('[type="submit"]').textContent = 'Guardar';
            }
        }
        
        AppUtils.showModal(this.modalId);
    }
    
    closeModal() {
        AppUtils.hideModal(this.modalId);
        this.currentId = null;
        document.getElementById(this.formId).reset();
    }
    
    async save(event) {
        event.preventDefault();
        
        const formData = {};
        this.fields.forEach(field => {
            const element = document.getElementById(field.name);
            if (element) {
                if (element.type === 'checkbox') {
                    formData[field.name] = element.checked;
                } else if (element.type === 'number') {
                    formData[field.name] = parseFloat(element.value) || 0;
                } else {
                    formData[field.name] = element.value;
                }
            }
        });
        
        try {
            const result = this.currentId 
                ? await AppUtils.update(this.entityNamePlural.toLowerCase(), this.currentId, formData)
                : await AppUtils.create(this.entityNamePlural.toLowerCase(), formData);
            
            this.closeModal();
            this.loadData();
            AppUtils.showMessage(`${this.entityName} guardado exitosamente`, 'success');
        } catch (error) {
            console.error('Error:', error);
            AppUtils.showMessage(`Error al guardar ${this.entityName}: ${error.message}`, 'error');
        }
    }
      edit(id) {
        this.openModal(id);
    }
    
    async deleteItem(id) {
        if (!confirm(`¿Está seguro de eliminar este ${this.entityName}?`)) return;
        
        try {
            await AppUtils.delete(this.entityNamePlural.toLowerCase(), id);
            this.loadData();
            AppUtils.showMessage(`${this.entityName} eliminado exitosamente`, 'success');
        } catch (error) {
            console.error('Error:', error);
            AppUtils.showMessage(`Error al eliminar ${this.entityName}: ${error.message}`, 'error');
        }
    }
    
    // Método de compatibilidad
    delete(id) {
        return this.deleteItem(id);
    }
    
    filter() {
        const searchTerm = document.getElementById(this.searchInputId).value.toLowerCase();
        AppUtils.filterTable(this.tableBodyId, searchTerm, (row, term) => {
            return row.textContent.toLowerCase().includes(term);
        });
    }
    
    exportData() {
        const headers = this.fields.map(field => field.name);
        AppUtils.exportToCSV(this.data, headers, `${this.entityNamePlural.toLowerCase()}.csv`);
    }
    
    updateCount() {
        const countElement = document.querySelector('[id*="Count"]');
        if (countElement) {
            countElement.textContent = `Total: ${this.data.length} ${this.entityNamePlural.toLowerCase()}`;
        }
    }
    
    bindEvents() {
        // Bind form submit
        const form = document.getElementById(this.formId);
        if (form) {
            form.addEventListener('submit', (e) => this.save(e));
        }
        
        // Bind search
        const searchInput = document.getElementById(this.searchInputId);
        if (searchInput) {
            searchInput.addEventListener('keyup', () => this.filter());
        }
        
        // Bind modal events
        const modal = document.getElementById(this.modalId);
        if (modal) {
            modal.addEventListener('show.bs.modal', () => {
                // Modal is opening - could add additional logic here
            });
            
            modal.addEventListener('hidden.bs.modal', () => {
                this.currentId = null;
                if (document.getElementById(this.formId)) {
                    document.getElementById(this.formId).reset();
                }
            });
        }
    }
}