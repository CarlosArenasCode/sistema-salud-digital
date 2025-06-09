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
    private String numeroIdentificacion;
    private String emergencyContact;
    private String emergencyPhone;
    private String bloodType;
    private String allergies;
    private String seguroMedico;
    private String estadoCivil;
    private String ocupacion;
}
