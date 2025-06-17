package com.clinica.salud.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * DTO específico para pacientes que extiende PersonaDTO
 */
@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class PacienteDTO extends PersonaDTO {
    
    // Datos de identificación del paciente
    private String numeroIdentificacion;
    
    // Datos de contacto para emergencias
    private String emergencyContact;
    private String emergencyPhone;
    
    // Información médica relevante
    private String bloodType;
    private String allergies;
    private String seguroMedico;
    
    // Información sociodemográfica
    private String estadoCivil;
    private String ocupacion;
}
