package com.clinica.salud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// DTO base
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseDTO {    
    // Identificador único de la entidad
    protected Long id;
    // Fecha y hora de creación del registro
    protected LocalDateTime fechaCreacion;
    // Fecha y hora de última actualización
    protected LocalDateTime fechaActualizacion;    
    // Método que verifica si la entidad es nueva basándose en el ID
    public boolean esNuevo() {
        return id == null;    }
    
    // Método que retorna información de auditoría formateada
    public String getInfoAuditoria() {
        
        StringBuilder info = new StringBuilder();
        info.append("Creado: ").append(fechaCreacion);
        
        if (fechaActualizacion != null && !fechaActualizacion.equals(fechaCreacion)) {
            info.append(", Actualizado: ").append(fechaActualizacion);
        }
        
        return info.toString();
    }
}
