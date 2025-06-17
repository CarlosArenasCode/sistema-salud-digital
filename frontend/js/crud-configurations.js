// Configuraciones centralizadas para CRUDManager
// Reduce duplicación de código en páginas HTML

class CRUDConfigurations {
    
    //==================================================
    // CONFIGURACIÓN BASE
    //==================================================
    
    /**
     * Configuración base común para todas las entidades
     * Proporciona valores predeterminados para los componentes del CRUD
     */
    static getBaseConfig() {
        return {
            searchInputId: 'searchInput',
            exportButtonId: 'exportBtn'
        };
    }
    
    //==================================================
    // CONFIGURACIONES DE PERSONAL MÉDICO
    //==================================================
    
    /**
     * Configuración específica para Pacientes
     * Define los campos, formato de tabla y propiedades para la entidad Paciente
     */
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
            ],
            // Función personalizada para formatear filas de tabla
            formatTableRow: function(item) {
                return `
                    <tr>
                        <td>${item.nombreCompleto || (item.nombres + ' ' + item.apellidos)}</td>
                        <td>${item.numeroIdentificacion || ''}</td>
                        <td>${item.telefono || ''}</td>
                        <td>${item.email || ''}</td>                        <td class="table-actions">
                            <button class="btn btn-view btn-sm" onclick="pacienteManager.view(${item.id})">👁️</button>
                            <button class="btn btn-edit btn-sm" data-bs-toggle="modal" data-bs-target="#patientModal" onclick="pacienteManager.edit(${item.id})">✏️</button>
                            <button class="btn btn-delete btn-sm" onclick="pacienteManager.delete(${item.id})">🗑️</button>
                        </td>
                    </tr>
                `;
            }
        };
    }
    
    /**
     * Configuración específica para Médicos
     * Define los campos, formato de tabla y propiedades para la entidad Médico
     */
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
                        <td>${item.email || ''}</td>                        <td class="table-actions">
                            <button class="btn btn-view btn-sm" onclick="medicoManager.view(${item.id})">👁️</button>
                            <button class="btn btn-edit btn-sm" data-bs-toggle="modal" data-bs-target="#doctorModal" onclick="medicoManager.edit(${item.id})">✏️</button>
                            <button class="btn btn-delete btn-sm" onclick="medicoManager.delete(${item.id})">🗑️</button>
                        </td>
                    </tr>
                `;
            }
        };
    }
    
    //==================================================
    // CONFIGURACIONES DE INVENTARIO
    //==================================================
    
    /**
     * Configuración específica para Medicamentos
     * Define los campos, formato de tabla y propiedades para la entidad Medicamento
     */
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
                        <td>${item.fabricante || ''}</td>                        <td class="table-actions">
                            <button class="btn btn-view btn-sm" onclick="medicamentoManager.view(${item.id})">👁️</button>
                            <button class="btn btn-edit btn-sm" data-bs-toggle="modal" data-bs-target="#medicineModal" onclick="medicamentoManager.edit(${item.id})">✏️</button>
                            <button class="btn btn-delete btn-sm" onclick="medicamentoManager.delete(${item.id})">🗑️</button>
                        </td>
                    </tr>
                `;
            }
        };
    }
    
    //==================================================
    // CONFIGURACIONES DE SERVICIOS MÉDICOS
    //==================================================
        /**
     * Configuración específica para Citas
     * Define los campos y propiedades para la gestión de citas médicas
     */
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
            ],
            // Función personalizada para formatear filas de tabla
            formatTableRow: function(item) {
                return `
                    <tr>
                        <td>${item.fechaCita || ''}</td>
                        <td>${item.pacienteNombre || ''}</td>
                        <td>${item.medicoNombre || ''}</td>
                        <td>${item.motivoConsulta || ''}</td>
                        <td><span class="badge bg-${item.estado === 'COMPLETADA' ? 'success' : item.estado === 'CANCELADA' ? 'danger' : 'warning'}">${item.estado || ''}</span></td>                        <td class="table-actions">
                            <button class="btn btn-view btn-sm" onclick="citaManager.view(${item.id})">👁️</button>
                            <button class="btn btn-edit btn-sm" data-bs-toggle="modal" data-bs-target="#citaModal" onclick="citaManager.edit(${item.id})">✏️</button>
                            <button class="btn btn-delete btn-sm" onclick="citaManager.delete(${item.id})">🗑️</button>
                        </td>
                    </tr>
                `;
            }
        };
    }
    
    //==================================================
    // CONFIGURACIONES DE DOCUMENTACIÓN CLÍNICA
    //==================================================
      /**
     * Configuración específica para Historiales Médicos
     * Define los campos y propiedades para gestionar el historial clínico
     */
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
            ],
            // Función personalizada para formatear filas de tabla
            formatTableRow: function(item) {
                return `
                    <tr>
                        <td>${item.fecha || ''}</td>
                        <td>${item.pacienteNombre || ''}</td>
                        <td>${item.medicoNombre || ''}</td>
                        <td>${item.diagnostico || ''}</td>
                        <td>${item.tratamiento || ''}</td>                        <td class="table-actions">
                            <button class="btn btn-view btn-sm" onclick="historialManager.view(${item.id})">👁️</button>
                            <button class="btn btn-edit btn-sm" data-bs-toggle="modal" data-bs-target="#historialModal" onclick="historialManager.edit(${item.id})">✏️</button>
                            <button class="btn btn-delete btn-sm" onclick="historialManager.delete(${item.id})">🗑️</button>
                        </td>
                    </tr>
                `;
            }
        };
    }
      /**
     * Configuración específica para Resultados de Laboratorio
     * Define los campos y propiedades para gestionar los exámenes médicos
     */
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
            ],
            // Función personalizada para formatear filas de tabla
            formatTableRow: function(item) {
                return `
                    <tr>
                        <td>${item.fecha || ''}</td>
                        <td>${item.pacienteNombre || ''}</td>
                        <td>${item.tipoExamen || ''}</td>
                        <td>${item.resultado || ''}</td>
                        <td>${item.valorReferencia || ''}</td>                        <td class="table-actions">
                            <button class="btn btn-view btn-sm" onclick="resultadoManager.view(${item.id})">👁️</button>
                            <button class="btn btn-edit btn-sm" data-bs-toggle="modal" data-bs-target="#resultadoModal" onclick="resultadoManager.edit(${item.id})">✏️</button>
                            <button class="btn btn-delete btn-sm" onclick="resultadoManager.delete(${item.id})">🗑️</button>
                        </td>
                    </tr>
                `;
            }
        };
    }
    
    //==================================================
    // MÉTODOS DE UTILIDAD
    //==================================================
    
    /**
     * Método para obtener configuración por nombre de entidad
     * Facilita la obtención de una configuración específica basada en el tipo de entidad
     */
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
