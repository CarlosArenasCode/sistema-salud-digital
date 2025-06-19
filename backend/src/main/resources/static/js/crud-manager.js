// Clase genérica para manejo de formularios CRUD con operaciones básicas
class CRUDManager {
    
    // Constructor que inicializa la configuración y propiedades de la entidad
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
    
    // Inicializa el manager cargando datos y vinculando eventos
    init() {
        this.loadData();
        this.bindEvents();
    }
    
    // Carga los datos de la entidad desde el API
    async loadData() {
        try {
            console.log(`Cargando datos de ${this.entityNamePlural}...`);
            console.log(`URL: ${AppUtils.API_BASE}/${this.entityNamePlural.toLowerCase()}`);
            
            this.data = await AppUtils.getAll(this.entityNamePlural.toLowerCase());
            console.log(`Datos cargados:`, this.data);
            
            this.renderTable();
            this.updateCount();
        } catch (error) {
            console.error(`Error cargando ${this.entityNamePlural}:`, error);
            AppUtils.showMessage(`Error cargando ${this.entityNamePlural}: ${error.message}`, 'error');        }
    }
    
    // Renderiza la tabla con los datos cargados
    renderTable() {
        const tbody = document.getElementById(this.tableBodyId);
        if (tbody) {
            tbody.innerHTML = this.data.map(item => this.renderTableRow(item)).join('');        }
    }
    
    // Renderiza una fila de la tabla usando configuración personalizada o genérica
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
            </tr>        `;
    }
    
    // Actualiza el contador de elementos mostrados
    updateCount() {
        const countElement = document.querySelector('[id*="Count"]');
        if (countElement) {
            countElement.textContent = `Total: ${this.data.length} ${this.entityNamePlural.toLowerCase()}`;        }
    }
    
    // Abre el modal para crear o editar un elemento
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
    
    // Cierra el modal y resetea el formulario
    closeModal() {
        AppUtils.hideModal(this.modalId);
        this.currentId = null;        document.getElementById(this.formId).reset();
    }
    
    // Guarda o actualiza un elemento enviando datos al API
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
            await this.loadData();
            AppUtils.showMessage(`${this.entityName} guardado exitosamente`, 'success');
        } catch (error) {
            console.error('Error:', error);
            // En caso de error 500, recargar datos y mostrar advertencia
            await this.loadData();
            if (error.message.includes('500')) {
                AppUtils.showMessage(`${this.entityName} guardado aunque el servidor devolvió 500`, 'warning');
            } else {
                AppUtils.showMessage(`Error al guardar ${this.entityName}: ${error.message}`, 'error');
            }        }
    }
    
    // Abre el modal en modo edición para un elemento específico
    edit(id) {
        this.openModal(id);
    }
    
    // Elimina un elemento después de confirmación del usuario
    async deleteItem(id) {
        if (!confirm(`¿Está seguro de eliminar este ${this.entityName}?`)) return;
        
        try {
            await AppUtils.delete(this.entityNamePlural.toLowerCase(), id);
            this.loadData();
            AppUtils.showMessage(`${this.entityName} eliminado exitosamente`, 'success');
        } catch (error) {
            console.error('Error:', error);
            AppUtils.showMessage(`Error al eliminar ${this.entityName}: ${error.message}`, 'error');        }
    }
    
    // Método de compatibilidad para el método delete
    delete(id) {
        return this.deleteItem(id);
    }
    
    // Filtra los elementos de la tabla según el término de búsqueda
    filter() {
        const searchTerm = document.getElementById(this.searchInputId).value.toLowerCase();
        AppUtils.filterTable(this.tableBodyId, searchTerm, (row, term) => {
            return row.textContent.toLowerCase().includes(term);        });
    }    // Exporta los datos a un archivo PDF (o CSV como fallback) - VERSIÓN ACTUALIZADA 2025-06-18
    exportData() {
        console.log('=== EXPORTACIÓN INICIADA - VERSIÓN BACKEND 2025-06-18 ===');
        console.log('Iniciando exportación...');
        console.log('window.jspdf:', typeof window.jspdf);
        console.log('window.jsPDF:', typeof window.jsPDF);
        
        // Verificar múltiples formas de detectar jsPDF
        const hasJsPDF = (typeof window.jspdf !== 'undefined' && window.jspdf.jsPDF) || 
                        (typeof window.jsPDF !== 'undefined');
        
        console.log('jsPDF disponible:', hasJsPDF);
          // Crear encabezados más legibles y filtrar campos importantes para PDF
        const allHeaders = this.fields.map(field => {
            // Convertir nombres de campo a formato más legible
            const fieldName = field.name;
            const headerMap = {
                'nombres': 'Nombres',
                'apellidos': 'Apellidos',
                'numeroIdentificacion': 'Documento',
                'fechaNacimiento': 'Fecha Nac.',
                'genero': 'Género',
                'telefono': 'Teléfono',
                'email': 'Email',
                'direccion': 'Dirección',
                'contactoEmergencia': 'Contacto Emerg.',
                'telefonoEmergencia': 'Tel. Emerg.',
                'tipoSangre': 'Tipo Sangre',
                'alergias': 'Alergias',
                'seguroMedico': 'Seguro Médico',
                'estadoCivil': 'Estado Civil',
                'ocupacion': 'Ocupación',
                'activo': 'Activo',
                'especializacion': 'Especialidad',
                'numeroLicencia': 'Licencia',
                'anosExperiencia': 'Años Exp.',
                'tarifaConsulta': 'Tarifa',
                'consultorio': 'Consultorio',
                'universidad': 'Universidad'
            };
            return {
                fieldName: fieldName,
                header: headerMap[fieldName] || fieldName
            };
        });
        
        // Para PDF, seleccionar solo campos importantes para evitar sobrecarga
        const importantFields = ['nombres', 'apellidos', 'numeroIdentificacion', 'telefono', 'email', 'especializacion', 'activo'];
        const pdfFields = allHeaders.filter(field => 
            importantFields.includes(field.fieldName) || this.fields.length <= 6
        );
        
        // Si tenemos pocos campos, usar todos; si tenemos muchos, usar solo los importantes
        const selectedFields = this.fields.length <= 8 ? allHeaders : pdfFields;
        const headers = selectedFields.map(field => field.header);
        const fieldNames = selectedFields.map(field => field.fieldName);
        const title = `Reporte de ${this.entityNamePlural}`;
        
        // Intentar exportar como PDF si está disponible
        if (hasJsPDF) {
            console.log('Usando exportación PDF');
            AppUtils.exportToPDF(this.data, fieldNames, headers, `${this.entityNamePlural.toLowerCase()}.pdf`, title);
        } else {
            console.log('jsPDF no disponible, usando fallback CSV');
            // Fallback a la función de CSV
            AppUtils.exportToCSV(this.data, fieldNames, `${this.entityNamePlural.toLowerCase()}.csv`);
        }
    }
    
    // Vincula eventos del DOM a los métodos correspondientes
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