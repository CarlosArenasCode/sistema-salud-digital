package com.clinica.salud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

// DTO genérico para personas (pacientes y médicos) con campos comunes
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PersonaDTO {    
    // Identificador único de la persona
    private Long id;
    
    // Nombre de la persona
    private String firstName;
    // Apellido de la persona
    private String lastName;
    // Género de la persona
    private String gender;
    // Fecha de nacimiento
    private LocalDate dateOfBirth;
    
    // Número de teléfono de contacto
    private String phone;
    // Dirección de correo electrónico
    private String email;
    // Dirección física de residencia
    private String address;
    
    // Fecha y hora de creación del registro
    private LocalDateTime createdAt;
    // Fecha y hora de última actualización
    private LocalDateTime updatedAt;    
    // Método que devuelve el nombre completo concatenado
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
