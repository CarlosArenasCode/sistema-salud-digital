package com.clinica.salud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO genérico para personas (pacientes y médicos)
 * Reduce duplicación de campos comunes
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PersonaDTO {
    
    // -------------------------------------------------
    // Identificación
    // -------------------------------------------------
    private Long id;
    
    // -------------------------------------------------
    // Información personal
    // -------------------------------------------------
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate dateOfBirth;
    
    // -------------------------------------------------
    // Información de contacto
    // -------------------------------------------------
    private String phone;
    private String email;
    private String address;
    
    // -------------------------------------------------
    // Metadatos del registro
    // -------------------------------------------------
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // -------------------------------------------------
    // Métodos utilitarios
    // -------------------------------------------------
    /**
     * Devuelve el nombre completo de la persona
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
