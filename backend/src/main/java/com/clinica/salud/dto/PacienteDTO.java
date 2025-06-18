package com.clinica.salud.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

// DTO específico para pacientes que extiende PersonaDTO
@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class PacienteDTO extends PersonaDTO {    
    // Número de identificación oficial del paciente
    private String numeroIdentificacion;
    
    // Nombre del contacto de emergencia
    private String emergencyContact;
    // Teléfono del contacto de emergencia
    private String emergencyPhone;
    
    // Tipo de sangre del paciente
    private String bloodType;
    // Alergias conocidas del paciente
    private String allergies;
    // Compañía de seguro médico
    private String seguroMedico;
    
    // Estado civil del paciente
    private String estadoCivil;
    // Ocupación laboral del paciente
    private String ocupacion;
}
