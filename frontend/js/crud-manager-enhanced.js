// CRUDManager Enhanced - Versión optimizada con más funcionalidades
class CRUDManagerEnhanced extends CRUDManager {
    
    constructor(config) {
        super(config);
        this.validationRules = config.validationRules || {};
        this.customRenderers = config.customRenderers || {};
        this.filters = config.filters || [];
        this.sortConfig = config.sortConfig || { field: 'id', direction: 'asc' };
    }
    
    // Validación mejorada
    validateForm() {
        const errors = {};
        
        this.fields.forEach(field => {
            const element = document.getElementById(field.name);
            const value = element ? element.value.trim() : '';
            
            // Validación de campos requeridos
            if (field.required && !value) {
                errors[field.name] = `${field.name} es requerido`;
                return;
            }
            
            // Validaciones específicas por tipo
            if (value && this.validationRules[field.name]) {
                const rule = this.validationRules[field.name];
                if (rule.pattern && !rule.pattern.test(value)) {
                    errors[field.name] = rule.message || `${field.name} tiene formato inválido`;
                }
                if (rule.minLength && value.length < rule.minLength) {
                    errors[field.name] = `${field.name} debe tener al menos ${rule.minLength} caracteres`;
                }
                if (rule.maxLength && value.length > rule.maxLength) {
                    errors[field.name] = `${field.name} no puede tener más de ${rule.maxLength} caracteres`;
                }
            }
        });
        
        return errors;
    }
    
    // Renderizado mejorado de tabla con sorting
    renderTable() {
        const tbody = document.getElementById(this.tableBodyId);
        if (!tbody) return;
        
        // Aplicar ordenamiento
        const sortedData = this.sortData([...this.data]);
        
        tbody.innerHTML = sortedData.map(item => this.renderTableRow(item)).join('');
        this.updateCount();
    }
    
    // Ordenamiento de datos
    sortData(data) {
        return data.sort((a, b) => {
            const fieldA = a[this.sortConfig.field];
            const fieldB = b[this.sortConfig.field];
            
            if (fieldA === fieldB) return 0;
            
            const comparison = fieldA > fieldB ? 1 : -1;
            return this.sortConfig.direction === 'asc' ? comparison : -comparison;
        });
    }
    
    // Renderizado personalizado de celda
    renderTableCell(item, field) {
        const value = item[field.name];
        
        // Usar renderer personalizado si existe
        if (this.customRenderers[field.name]) {
            return this.customRenderers[field.name](value, item);
        }
        
        // Renderizado por defecto según tipo
        switch (field.type) {
            case 'date':
                return value ? new Date(value).toLocaleDateString() : 'N/A';
            case 'email':
                return value ? `<a href="mailto:${value}">${value}</a>` : 'N/A';
            case 'tel':
                return value ? `<a href="tel:${value}">${value}</a>` : 'N/A';
            case 'number':
                return value !== undefined ? value.toLocaleString() : 'N/A';
            case 'select':
                // Si es un objeto, mostrar su representación string
                return typeof value === 'object' ? (value.nombre || value.descripcion || value.toString()) : value;
            default:
                return value || 'N/A';
        }
    }
    
    // Filtrado avanzado
    applyFilters() {
        if (this.filters.length === 0) return this.data;
        
        return this.data.filter(item => {
            return this.filters.every(filter => {
                const fieldValue = item[filter.field];
                const filterValue = filter.value;
                
                switch (filter.operator) {
                    case 'equals':
                        return fieldValue === filterValue;
                    case 'contains':
                        return fieldValue && fieldValue.toString().toLowerCase().includes(filterValue.toLowerCase());
                    case 'greaterThan':
                        return Number(fieldValue) > Number(filterValue);
                    case 'lessThan':
                        return Number(fieldValue) < Number(filterValue);
                    case 'dateAfter':
                        return new Date(fieldValue) > new Date(filterValue);
                    case 'dateBefore':
                        return new Date(fieldValue) < new Date(filterValue);
                    default:
                        return true;
                }
            });
        });
    }
    
    // Agregar filtro
    addFilter(field, operator, value) {
        this.filters = this.filters.filter(f => f.field !== field); // Remover filtro existente del mismo campo
        if (value) {
            this.filters.push({ field, operator, value });
        }
        this.renderTable();
    }
    
    // Limpiar filtros
    clearFilters() {
        this.filters = [];
        this.renderTable();
    }
    
    // Cambiar ordenamiento
    changeSorting(field) {
        if (this.sortConfig.field === field) {
            this.sortConfig.direction = this.sortConfig.direction === 'asc' ? 'desc' : 'asc';
        } else {
            this.sortConfig.field = field;
            this.sortConfig.direction = 'asc';
        }
        this.renderTable();
    }
    
    // Guardar con validación mejorada
    async save(event) {
        event.preventDefault();
        
        const validationErrors = this.validateForm();
        
        if (Object.keys(validationErrors).length > 0) {
            this.showValidationErrors(validationErrors);
            return;
        }
        
        // Continuar con el guardado original
        await super.save(event);
    }
    
    // Mostrar errores de validación
    showValidationErrors(errors) {
        // Limpiar errores anteriores
        document.querySelectorAll('.validation-error').forEach(el => el.remove());
        
        Object.keys(errors).forEach(fieldName => {
            const field = document.getElementById(fieldName);
            if (field) {
                field.classList.add('is-invalid');
                
                const errorDiv = document.createElement('div');
                errorDiv.className = 'validation-error text-danger small mt-1';
                errorDiv.textContent = errors[fieldName];
                
                field.parentNode.appendChild(errorDiv);
            }
        });
        
        AppUtils.showMessage('Por favor corrige los errores en el formulario', 'error');
    }
    
    // Limpiar errores de validación
    clearValidationErrors() {
        document.querySelectorAll('.is-invalid').forEach(el => el.classList.remove('is-invalid'));
        document.querySelectorAll('.validation-error').forEach(el => el.remove());
    }
    
    // Override del método openModal para limpiar errores
    openModal(id = null) {
        this.clearValidationErrors();
        super.openModal(id);
    }
}
