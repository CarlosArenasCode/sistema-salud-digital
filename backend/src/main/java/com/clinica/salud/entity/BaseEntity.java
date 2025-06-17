package com.clinica.salud.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Clase abstracta base para todas las entidades
 * Proporciona estructura y comportamiento común
 */
@MappedSuperclass
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class BaseEntity {
    
    // ===============================================
    // Identificador único
    // ===============================================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    protected Long id;
    
    // ===============================================
    // Campos de auditoría
    // ===============================================
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    protected LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    protected LocalDateTime fechaActualizacion;
    
    // ===============================================
    // Métodos de ciclo de vida
    // ===============================================
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}
