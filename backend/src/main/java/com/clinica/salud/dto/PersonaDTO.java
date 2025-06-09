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
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String address;
    private String gender;
    private LocalDate dateOfBirth;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Método utilitario
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
