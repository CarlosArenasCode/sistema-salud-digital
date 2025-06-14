package com.clinica.salud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO base genérico que proporciona campos comunes
 * Reduce duplicación en objetos de transferencia de datos
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseDTO {
    
    protected Long id;
    protected LocalDateTime fechaCreacion;
    protected LocalDateTime fechaActualizacion;
    
    // Método de utilidad para validar si es una entidad nueva
    public boolean esNuevo() {
        return id == null;
    }
    
    // Método de utilidad para obtener información de auditoría
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
