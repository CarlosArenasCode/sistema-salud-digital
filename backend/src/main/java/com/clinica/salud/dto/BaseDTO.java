package com.clinica.salud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO base genérico que proporciona campos comunes
 * Reduce duplicación en objetos de transferencia de datos
 */

// =====================================================
// CONFIGURACIÓN DE CLASE
// =====================================================
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseDTO {
    
    // =====================================================
    // ATRIBUTOS PRINCIPALES
    // =====================================================
    protected Long id;
    protected LocalDateTime fechaCreacion;
    protected LocalDateTime fechaActualizacion;
    
    // =====================================================
    // MÉTODOS DE VALIDACIÓN
    // =====================================================
    
    /**
     * Método que determina si la entidad es nueva basándose en el ID
     * @return true si es una entidad nueva, false si ya existe
     */
    public boolean esNuevo() {
        return id == null;
    }
    
    // =====================================================
    // MÉTODOS DE AUDITORÍA
    // =====================================================
    
    /**
     * Método para obtener información de auditoría formateada
     * @return cadena con la información de creación y actualización
     */
    public String getInfoAuditoria() {
        if (fechaCreacion == null) {
            return "Sin información de auditoría";
        }
        
        StringBuilder info = new StringBuilder();
        info.append("Creado: ").append(fechaCreacion);
        
        if (fechaActualizacion != null && !fechaActualizacion.equals(fechaCreacion)) {
            info.append(", Actualizado: ").append(fechaActualizacion);
        }
        
        return info.toString();
    }
}
