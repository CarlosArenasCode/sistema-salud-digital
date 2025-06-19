// Utilidades comunes para el frontend del sistema de salud
class AppUtils {
    
    // URL base para las peticiones a la API del backend
    static API_BASE = 'http://localhost:8080/api';
    
    // Realiza peticiones HTTP genéricas a la API con configuración básica
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
            throw error;        }
    }
    
    // Obtiene todos los elementos de una entidad específica
    static async getAll(entity) {
        const response = await this.apiRequest(`/${entity}`);
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error(`Error fetching data: ${response.status}`);        }
    }
    
    // Obtiene datos de un endpoint específico
    static async get(endpoint) {
        const response = await this.apiRequest(endpoint);
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error(`Error fetching data: ${response.status}`);        }
    }
    
    // Obtiene un elemento específico por su ID
    static async getById(entity, id) {
        const response = await this.apiRequest(`/${entity}/${id}`);
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error(`Error fetching data: ${response.status}`);        }
    }
    
    // Crea un nuevo elemento enviando datos a la API
    static async create(entity, data) {
        const response = await this.apiRequest(`/${entity}`, 'POST', data);
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error(`Error creating data: ${response.status}`);        }
    }
    
    // Actualiza un elemento existente por su ID
    static async update(entity, id, data) {
        const response = await this.apiRequest(`/${entity}/${id}`, 'PUT', data);
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error(`Error updating data: ${response.status}`);        }
    }
    
    // Elimina un elemento por su ID manejando respuestas vacías
    static async delete(entity, id) {
        const response = await this.apiRequest(`/${entity}/${id}`, 'DELETE');
        if (response.ok) {
            // Manejar respuesta vacía o con contenido
            if (response.status === 204) {
                return null; // No Content - eliminación exitosa
            }
            // Verificar si hay contenido para parsear
            const contentType = response.headers.get('content-type');
            if (contentType && contentType.includes('application/json')) {
                const text = await response.text();
                return text ? JSON.parse(text) : null;
            }
            return null;
        } else {
            throw new Error(`Error deleting data: ${response.status}`);        }
    }
    
    // Muestra un modal de Bootstrap por su ID
    static showModal(modalId) {
        const modalEl = document.getElementById(modalId);
        const modal = bootstrap.Modal.getOrCreateInstance(modalEl);        modal.show();
    }

    // Oculta un modal de Bootstrap por su ID
    static hideModal(modalId) {
        const modalEl = document.getElementById(modalId);
        const modal = bootstrap.Modal.getOrCreateInstance(modalEl);        modal.hide();
    }
    
    // Filtra elementos en una tabla según un criterio de búsqueda
    static filterTable(tableBodyId, searchTerm, filterFunction) {
        const tbody = document.getElementById(tableBodyId);
        const rows = tbody.querySelectorAll('tr');
        
        rows.forEach(row => {
            const matches = filterFunction(row, searchTerm);
            row.style.display = matches ? '' : 'none';        });
    }    // Exporta datos a PDF o como ventana de impresión
    static exportToCSV(data, headers, filename) {
        // Intentar primero con jsPDF si está disponible
        if (typeof window.jspdf !== 'undefined') {
            console.log('Usando jsPDF para exportación');
            try {
                const { jsPDF } = window.jspdf;
                const doc = new jsPDF();
                
                // Configurar fuente
                doc.setFont('helvetica');
                
                // Título
                doc.setFontSize(16);
                doc.setTextColor(0, 123, 255);
                doc.text('Sistema de Salud Digital', 20, 20);
                
                // Fecha
                doc.setFontSize(10);
                doc.text(`Generado: ${new Date().toLocaleDateString('es-ES')}`, 20, 30);
                
                // Crear tabla simple
                let y = 50;
                doc.setFontSize(12);
                doc.setFont('helvetica', 'bold');
                
                // Encabezados
                headers.forEach((header, index) => {
                    doc.text(header, 20 + (index * 35), y);
                });
                
                // Datos
                doc.setFont('helvetica', 'normal');
                doc.setFontSize(10);
                data.forEach((row, rowIndex) => {
                    y += 15;
                    if (y > 270) { // Nueva página
                        doc.addPage();
                        y = 30;
                    }
                    headers.forEach((header, cellIndex) => {
                        const value = row[header] || '';
                        doc.text(String(value).substring(0, 20), 20 + (cellIndex * 35), y);
                    });
                });
                
                // Descargar
                const pdfFilename = filename.replace(/\.csv$/, '') + '.pdf';
                doc.save(pdfFilename);
                return;
            } catch (error) {
                console.error('Error con jsPDF:', error);
            }
        }
        
        console.log('Usando ventana de impresión como fallback');
        // Fallback: crear ventana de impresión con formato HTML
        const htmlContent = `
            <!DOCTYPE html>
            <html>
                <head>
                    <meta charset="utf-8">
                    <title>Reporte - Sistema de Salud Digital</title>
                    <style>
                        body { font-family: Arial, sans-serif; margin: 20px; }
                        h1 { color: #007bff; text-align: center; }
                        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
                        th { background-color: #007bff; color: white; padding: 10px; text-align: left; }
                        td { padding: 8px; border-bottom: 1px solid #ddd; }
                        tr:nth-child(even) { background-color: #f9f9f9; }
                        @media print {
                            button { display: none; }
                        }
                    </style>
                </head>
                <body>
                    <h1>Sistema de Salud Digital</h1>
                    <p><strong>Fecha:</strong> ${new Date().toLocaleDateString('es-ES')}</p>
                    <button onclick="window.print()" style="margin-bottom: 20px; padding: 10px 20px; background: #007bff; color: white; border: none; cursor: pointer;">Imprimir/Guardar PDF</button>
                    <table>
                        <thead>
                            <tr>
                                ${headers.map(header => `<th>${header}</th>`).join('')}
                            </tr>
                        </thead>
                        <tbody>
                            ${data.map(row => 
                                `<tr>
                                    ${headers.map(header => `<td>${row[header] || ''}</td>`).join('')}
                                </tr>`
                            ).join('')}
                        </tbody>
                    </table>
                </body>
            </html>
        `;

        const printWindow = window.open('', '_blank');
        printWindow.document.write(htmlContent);
        printWindow.document.close();
    }    // Exporta datos a PDF usando jsPDF (requiere cargar la librería)
    static exportToPDF(data, fieldNames, headers, filename, title = 'Reporte de Datos') {
        // Verificar si jsPDF está disponible en múltiples formas
        let jsPDF = null;
        
        if (typeof window.jspdf !== 'undefined' && window.jspdf.jsPDF) {
            jsPDF = window.jspdf.jsPDF;
        } else if (typeof window.jsPDF !== 'undefined') {
            jsPDF = window.jsPDF;
        }
        
        if (!jsPDF) {
            console.error('jsPDF no está disponible');
            // Fallback: usar CSV si jsPDF no está disponible
            this.exportToCSV(data, fieldNames, filename);
            return;
        }

        console.log('Creando PDF con jsPDF...');
        const doc = new jsPDF();
        
        // Configurar fuente que soporte caracteres especiales
        doc.setFont('helvetica');
        
        // Título del documento
        doc.setFontSize(16);
        doc.setTextColor(0, 123, 255); // Color azul
        doc.text(title, 20, 20);
        
        // Subtítulo
        doc.setFontSize(12);
        doc.setTextColor(100, 100, 100);
        doc.text('Sistema de Salud Digital', 20, 30);
        
        // Fecha
        doc.setFontSize(10);
        doc.text(`Generado: ${new Date().toLocaleDateString('es-ES')}`, 20, 40);
        
        // Preparar datos para la tabla usando los nombres de campo
        const tableData = data.map(row => fieldNames.map(fieldName => row[fieldName] || ''));
        
        // Crear tabla usando autoTable (si está disponible)
        if (typeof doc.autoTable !== 'undefined') {
            console.log('Usando autoTable para generar tabla...');
            doc.autoTable({
                head: [headers],
                body: tableData,
                startY: 50,
                styles: {
                    fontSize: 8,
                    font: 'helvetica'
                },
                headStyles: {
                    fillColor: [0, 123, 255],
                    textColor: 255
                },
                alternateRowStyles: {
                    fillColor: [249, 249, 249]
                }
            });
        } else {
            console.log('autoTable no disponible, usando tabla simple...');
            // Fallback simple si autoTable no está disponible
            let y = 60;
            doc.setFontSize(9);
            
            // Encabezados
            doc.setFont('helvetica', 'bold');
            headers.forEach((header, index) => {
                doc.text(header, 20 + (index * 30), y);
            });
            
            // Datos
            doc.setFont('helvetica', 'normal');
            tableData.forEach((row, rowIndex) => {
                y += 10;
                row.forEach((cell, cellIndex) => {
                    doc.text(String(cell), 20 + (cellIndex * 30), y);
                });
            });
        }
        
        // Descargar el PDF
        const pdfFilename = filename.replace(/\.(csv|xls)$/, '') + '.pdf';
        console.log(`Descargando PDF: ${pdfFilename}`);
        doc.save(pdfFilename);
    }

    // Muestra mensajes de notificación tipo toast con diferentes tipos
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
            toastElement.remove();        });
    }
    
    // Obtiene o crea el contenedor de toasts para notificaciones
    static getOrCreateToastContainer() {
        let container = document.getElementById('toast-container');
        if (!container) {
            container = document.createElement('div');
            container.id = 'toast-container';
            container.className = 'position-fixed top-0 end-0 p-3';
            container.style.zIndex = '1055';
            document.body.appendChild(container);
        }        return container;
    }

    // Verifica si el usuario está autenticado comprobando el token
    static checkAuth() {
        const token = localStorage.getItem('auth_token');
        if (!token) {
            window.location.href = 'login.html';
            return false;
        }        return true;
    }
    
    // Cierra la sesión del usuario eliminando datos locales
    static logout() {
        if (confirm('¿Está seguro que desea cerrar sesión?')) {
            localStorage.removeItem('auth_token');
            localStorage.removeItem('user_data');
            window.location.href = 'login.html';        }
    }
    
    // Obtiene los datos del usuario actualmente logueado desde localStorage
    static getUserData() {
        return JSON.parse(localStorage.getItem('user_data') || '{}');
    }
}
