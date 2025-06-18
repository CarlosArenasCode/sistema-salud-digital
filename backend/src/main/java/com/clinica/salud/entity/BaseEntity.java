package com.clinica.salud.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

// Clase abstracta base para todas las entidades con auditoría automática
@MappedSuperclass
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class BaseEntity {    
    // Identificador único autogenerado de la entidad
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    protected Long id;
    
    // Fecha y hora de creación del registro (inmutable)
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    protected LocalDateTime fechaCreacion;
    
    // Fecha y hora de última actualización del registro
    @Column(name = "fecha_actualizacion")
    protected LocalDateTime fechaActualizacion;    
    // Método ejecutado automáticamente antes de persistir la entidad
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }
    
    // Método ejecutado automáticamente antes de actualizar la entidad
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}
