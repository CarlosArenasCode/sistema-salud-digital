package com.clinica.salud.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

// DTO específico para médicos que extiende PersonaDTO
@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class MedicoDTO extends PersonaDTO {    
    // Especialización médica del doctor
    private String specialization;
    // Número de licencia profesional
    private String licenseNumber;
    // Años de experiencia profesional
    private Integer yearsOfExperience;
    
    // Tarifa por consulta médica
    private BigDecimal consultationFee;
    
    // Días de la semana disponibles para consultas
    private String availableDays;
    // Horarios de atención disponibles
    private String availableHours;
    // Número o nombre del consultorio
    private String consultorio;
    
    // Universidad donde estudió medicina
    private String universidad;
}
