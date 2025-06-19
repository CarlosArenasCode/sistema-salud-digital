class CRUDConfigurations {
    
    // Configuración base común para todas las entidades con valores predeterminados
    static getBaseConfig() {
        return {
            searchInputId: 'searchInput',
            exportButtonId: 'exportBtn'        };
    }
    
    // Configuración específica para Pacientes definiendo campos y formato de tabla
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
            // Función personalizada para formatear filas de tabla de pacientes
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
            }        };
    }
    
    // Configuración específica para Médicos definiendo campos y formato de tabla
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
                { name: 'consultorio', type: 'text', required: false },                { name: 'anosExperiencia', type: 'number', required: false },
                { name: 'tarifaConsulta', type: 'number', required: false }
            ],
            // Función personalizada para formatear filas de tabla de médicos
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
            }        };
    }
    
    // Configuración específica para Medicamentos definiendo campos y formato de tabla
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
            // Función personalizada para formatear filas de tabla de medicamentos
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
            }        };
    }
    
    // Configuración específica para Citas definiendo campos y propiedades
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
                { name: 'motivoConsulta', type: 'textarea', required: true },                { name: 'estado', type: 'select', required: true, options: ['PROGRAMADA', 'CONFIRMADA', 'CANCELADA', 'COMPLETADA'] },
                { name: 'observaciones', type: 'textarea', required: false }
            ],
            // Función personalizada para formatear filas de tabla de citas
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
            }        };
    }    
    // Método para obtener configuración por nombre de entidad facilitando acceso específico
    static getConfigByEntity(entityName) {
        const configs = {
            'pacientes': this.getPacienteConfig(),
            'medicos': this.getMedicoConfig(),
            'medicamentos': this.getMedicamentoConfig(),
            'citas': this.getCitaConfig()
        };
        
        return configs[entityName.toLowerCase()] || null;
    }
}
