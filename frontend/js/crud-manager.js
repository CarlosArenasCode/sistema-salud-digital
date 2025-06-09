// Clase genérica para manejo de formularios CRUD
class CRUDManager {
    constructor(config) {
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
            const response = await AppUtils.getAll(this.entityNamePlural.toLowerCase());
            if (response.ok) {
                this.data = await response.json();
                this.renderTable();
                this.updateCount();
            }
        } catch (error) {
            console.error(`Error cargando ${this.entityNamePlural}:`, error);
        }
    }
    
    renderTable() {
        const tbody = document.getElementById(this.tableBodyId);
        tbody.innerHTML = this.data.map(item => this.renderTableRow(item)).join('');
    }
    
    renderTableRow(item) {
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
        // Seleccionar título dentro del modal (cualquier etiqueta con clase modal-title)
        const title = document.querySelector(`#${this.modalId} .modal-title`);
        
        if (id) {
            const item = this.data.find(d => d.id == id);
            title.textContent = `Editar ${this.entityName}`;
            this.populateForm(item);
        } else {
            title.textContent = `Nuevo ${this.entityName}`;
            form.reset();
        }
        
        AppUtils.showModal(this.modalId);
    }
    
    closeModal() {
        AppUtils.hideModal(this.modalId);
        this.currentId = null;
    }
    
    populateForm(data) {
        this.fields.forEach(field => {
            const element = document.getElementById(field.name);
            if (element && data[field.name] !== undefined) {
                element.value = data[field.name];
            }
        });
    }
    
    async save(event) {
        event.preventDefault();
        
        const formData = {};
        this.fields.forEach(field => {
            const element = document.getElementById(field.name);
            if (element) {
                formData[field.name] = element.value;
            }
        });
        
        try {
            const response = this.currentId 
                ? await AppUtils.update(this.entityNamePlural.toLowerCase(), this.currentId, formData)
                : await AppUtils.create(this.entityNamePlural.toLowerCase(), formData);
            
            if (response.ok) {
                this.closeModal();
                this.loadData();
                AppUtils.showMessage(`${this.entityName} guardado exitosamente`, 'success');
            } else {
                AppUtils.showMessage(`Error al guardar ${this.entityName}`, 'error');
            }
        } catch (error) {
            console.error('Error:', error);
            AppUtils.showMessage('Error de conexión', 'error');
        }
    }
    
    edit(id) {
        this.openModal(id);
    }
    
    async delete(id) {
        if (!confirm(`¿Está seguro de eliminar este ${this.entityName}?`)) return;
        
        try {
            const response = await AppUtils.delete(this.entityNamePlural.toLowerCase(), id);
            if (response.ok) {
                this.loadData();
                AppUtils.showMessage(`${this.entityName} eliminado exitosamente`, 'success');
            } else {
                AppUtils.showMessage(`Error al eliminar ${this.entityName}`, 'error');
            }
        } catch (error) {
            console.error('Error:', error);
            AppUtils.showMessage('Error de conexión', 'error');
        }
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
        
        // Bind modal close on outside click
        window.addEventListener('click', (event) => {
            const modal = document.getElementById(this.modalId);
            if (event.target === modal) {
                this.closeModal();
            }
        });
    }
}