// Configuraciones centralizadas para CRUDManager
// Reduce duplicación de código en páginas HTML

class CRUDConfigurations {
    
    // Configuración base común para todas las entidades
    static getBaseConfig() {
        return {
            searchInputId: 'searchInput',
            exportButtonId: 'exportBtn'
        };
    }    // Configuración específica para Pacientes
    static getPacienteConfig() {
        return {
            ...this.getBaseConfig(),
            entityName: 'Paciente',
            entityNamePlural: 'pacientes',
            modalId: 'patientModal',
            formId: 'patientForm',
            tableBodyId: 'patientsTableBody',
            fields: [
                { name: 'nombres', type: 'text', required: true },
                { name: 'apellidos', type: 'text', required: true },
                { name: 'email', type: 'email', required: true },
                { name: 'telefono', type: 'tel', required: false },
                { name: 'fechaNacimiento', type: 'date', required: true },
                { name: 'numeroIdentificacion', type: 'text', required: true },
                { name: 'direccion', type: 'text', required: false },
                { name: 'tipoSangre', type: 'select', required: false, options: ['A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-'] },
                { name: 'genero', type: 'select', required: false, options: ['MASCULINO', 'FEMENINO', 'OTRO'] }
            ],            // Función personalizada para formatear filas de tabla
            formatTableRow: function(item) {
                return `
                    <tr>
                        <td>${item.nombreCompleto || (item.nombres + ' ' + item.apellidos)}</td>
                        <td>${item.numeroIdentificacion || ''}</td>
                        <td>${item.telefono || ''}</td>
                        <td>${item.email || ''}</td>
                        <td class="table-actions">
                            <button class="btn btn-edit btn-sm" data-bs-toggle="modal" data-bs-target="#patientModal" onclick="pacienteManager.edit(${item.id})">✏️</button>
                            <button class="btn btn-delete btn-sm" onclick="pacienteManager.delete(${item.id})">🗑️</button>
                        </td>
                    </tr>
                `;
            }
        };
    }    // Configuración específica para Médicos
    static getMedicoConfig() {
        return {
            ...this.getBaseConfig(),
            entityName: 'Médico',
            entityNamePlural: 'medicos',
            modalId: 'doctorModal',
            formId: 'doctorForm',
            tableBodyId: 'doctorsTableBody',
            fields: [
                { name: 'nombres', type: 'text', required: true },
                { name: 'apellidos', type: 'text', required: true },
                { name: 'email', type: 'email', required: true },
                { name: 'telefono', type: 'tel', required: false },
                { name: 'especializacion', type: 'text', required: true },
                { name: 'numeroLicencia', type: 'text', required: true },
                { name: 'consultorio', type: 'text', required: false },
                { name: 'anosExperiencia', type: 'number', required: false },
                { name: 'tarifaConsulta', type: 'number', required: false }
            ],
            // Función personalizada para formatear filas de tabla
            formatTableRow: function(item) {
                return `
                    <tr>
                        <td>${item.nombreCompleto || (item.nombres + ' ' + item.apellidos)}</td>
                        <td>${item.especializacion || ''}</td>
                        <td>${item.numeroLicencia || ''}</td>
                        <td>${item.telefono || ''}</td>
                        <td>${item.email || ''}</td>
                        <td class="table-actions">
                            <button class="btn btn-edit btn-sm" data-bs-toggle="modal" data-bs-target="#doctorModal" onclick="medicoManager.edit(${item.id})">✏️</button>
                            <button class="btn btn-delete btn-sm" onclick="medicoManager.delete(${item.id})">🗑️</button>
                        </td>
                    </tr>
                `;
            }
        };
    }    // Configuración específica para Medicamentos
    static getMedicamentoConfig() {
        return {
            ...this.getBaseConfig(),
            entityName: 'Medicamento',
            entityNamePlural: 'medicamentos',
            modalId: 'medicineModal',
            formId: 'medicineForm',
            tableBodyId: 'medicinesTableBody',
            fields: [
                { name: 'nombre', type: 'text', required: true },
                { name: 'descripcion', type: 'textarea', required: false },
                { name: 'codigo', type: 'text', required: false },
                { name: 'fabricante', type: 'text', required: false },
                { name: 'categoria', type: 'text', required: true },
                { name: 'precio', type: 'number', required: true },
                { name: 'stock', type: 'number', required: true },
                { name: 'stockMinimo', type: 'number', required: false },
                { name: 'fechaVencimiento', type: 'date', required: false },
                { name: 'requiereReceta', type: 'checkbox', required: false }
            ],
            // Función personalizada para formatear filas de tabla
            formatTableRow: function(item) {
                return `
                    <tr>
                        <td>${item.nombre || ''}</td>
                        <td>${item.categoria || ''}</td>
                        <td>$${item.precio || '0'}</td>
                        <td>${item.stock || '0'}</td>
                        <td>${item.fabricante || ''}</td>
                        <td class="table-actions">
                            <button class="btn btn-edit btn-sm" data-bs-toggle="modal" data-bs-target="#medicineModal" onclick="medicamentoManager.edit(${item.id})">✏️</button>
                            <button class="btn btn-delete btn-sm" onclick="medicamentoManager.delete(${item.id})">🗑️</button>
                        </td>
                    </tr>
                `;
            }
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
                { name: 'idPaciente', type: 'select', required: true, endpoint: '/pacientes' },
                { name: 'idMedico', type: 'select', required: true, endpoint: '/medicos' },
                { name: 'fechaCita', type: 'datetime-local', required: true },
                { name: 'motivoConsulta', type: 'textarea', required: true },
                { name: 'estado', type: 'select', required: true, options: ['PROGRAMADA', 'CONFIRMADA', 'CANCELADA', 'COMPLETADA'] },
                { name: 'observaciones', type: 'textarea', required: false }
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
