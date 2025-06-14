// Configuraciones centralizadas para CRUDManager
// Reduce duplicación de código en páginas HTML

class CRUDConfigurations {
    
    // Configuración base común para todas las entidades
    static getBaseConfig() {
        return {
            searchInputId: 'searchInput',
            exportButtonId: 'exportBtn'
        };
    }
    
    // Configuración específica para Pacientes
    static getPacienteConfig() {
        return {
            ...this.getBaseConfig(),
            entityName: 'Paciente',
            entityNamePlural: 'pacientes',
            modalId: 'pacienteModal',
            formId: 'pacienteForm',
            tableBodyId: 'pacientesTableBody',
            fields: [
                { name: 'nombre', type: 'text', required: true },
                { name: 'apellido', type: 'text', required: true },
                { name: 'email', type: 'email', required: true },
                { name: 'telefono', type: 'tel', required: false },
                { name: 'fechaNacimiento', type: 'date', required: true },
                { name: 'cedula', type: 'text', required: true },
                { name: 'direccion', type: 'text', required: false },
                { name: 'tipoSangre', type: 'select', required: false, options: ['A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-'] }
            ]
        };
    }
    
    // Configuración específica para Médicos
    static getMedicoConfig() {
        return {
            ...this.getBaseConfig(),
            entityName: 'Médico',
            entityNamePlural: 'medicos',
            modalId: 'medicoModal',
            formId: 'medicoForm',
            tableBodyId: 'medicosTableBody',
            fields: [
                { name: 'nombre', type: 'text', required: true },
                { name: 'apellido', type: 'text', required: true },
                { name: 'email', type: 'email', required: true },
                { name: 'telefono', type: 'tel', required: false },
                { name: 'especialidad', type: 'text', required: true },
                { name: 'numeroLicencia', type: 'text', required: true },
                { name: 'consultorio', type: 'text', required: false }
            ]
        };
    }
    
    // Configuración específica para Medicamentos
    static getMedicamentoConfig() {
        return {
            ...this.getBaseConfig(),
            entityName: 'Medicamento',
            entityNamePlural: 'medicamentos',
            modalId: 'medicamentoModal',
            formId: 'medicamentoForm',
            tableBodyId: 'medicamentosTableBody',
            fields: [
                { name: 'nombre', type: 'text', required: true },
                { name: 'descripcion', type: 'textarea', required: false },
                { name: 'precio', type: 'number', required: true },
                { name: 'stock', type: 'number', required: true },
                { name: 'categoria', type: 'text', required: true },
                { name: 'laboratorio', type: 'text', required: false },
                { name: 'fechaVencimiento', type: 'date', required: false }
            ]
        };
    }
    
    // Configuración específica para Citas
    static getCitaConfig() {
        return {
            ...this.getBaseConfig(),
            entityName: 'Cita',
            entityNamePlural: 'citas',
            modalId: 'citaModal',
            formId: 'citaForm',
            tableBodyId: 'citasTableBody',
            fields: [
                { name: 'paciente', type: 'select', required: true, endpoint: '/pacientes' },
                { name: 'medico', type: 'select', required: true, endpoint: '/medicos' },
                { name: 'fecha', type: 'date', required: true },
                { name: 'hora', type: 'time', required: true },
                { name: 'motivo', type: 'textarea', required: true },
                { name: 'estado', type: 'select', required: true, options: ['PROGRAMADA', 'CONFIRMADA', 'CANCELADA', 'COMPLETADA'] }
            ]
        };
    }
    
    // Configuración específica para Historiales Médicos
    static getHistorialConfig() {
        return {
            ...this.getBaseConfig(),
            entityName: 'Historial Médico',
            entityNamePlural: 'historiales',
            modalId: 'historialModal',
            formId: 'historialForm',
            tableBodyId: 'historialesTableBody',
            fields: [
                { name: 'paciente', type: 'select', required: true, endpoint: '/pacientes' },
                { name: 'medico', type: 'select', required: true, endpoint: '/medicos' },
                { name: 'fecha', type: 'date', required: true },
                { name: 'diagnostico', type: 'textarea', required: true },
                { name: 'tratamiento', type: 'textarea', required: false },
                { name: 'observaciones', type: 'textarea', required: false }
            ]
        };
    }
    
    // Configuración específica para Resultados de Laboratorio
    static getResultadoConfig() {
        return {
            ...this.getBaseConfig(),
            entityName: 'Resultado de Laboratorio',
            entityNamePlural: 'resultados',
            modalId: 'resultadoModal',
            formId: 'resultadoForm',
            tableBodyId: 'resultadosTableBody',
            fields: [
                { name: 'paciente', type: 'select', required: true, endpoint: '/pacientes' },
                { name: 'tipoExamen', type: 'text', required: true },
                { name: 'fecha', type: 'date', required: true },
                { name: 'resultado', type: 'textarea', required: true },
                { name: 'valorReferencia', type: 'text', required: false },
                { name: 'observaciones', type: 'textarea', required: false }
            ]
        };
    }
    
    // Método para obtener configuración por nombre de entidad
    static getConfigByEntity(entityName) {
        const configs = {
            'pacientes': this.getPacienteConfig(),
            'medicos': this.getMedicoConfig(),
            'medicamentos': this.getMedicamentoConfig(),
            'citas': this.getCitaConfig(),
            'historiales': this.getHistorialConfig(),
            'resultados': this.getResultadoConfig()
        };
        
        return configs[entityName.toLowerCase()] || null;
    }
}
