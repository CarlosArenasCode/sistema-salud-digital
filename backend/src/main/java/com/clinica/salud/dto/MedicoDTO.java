package com.clinica.salud.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * DTO específico para médicos que extiende PersonaDTO
 */
@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class MedicoDTO extends PersonaDTO {
    private String specialization;
    private String licenseNumber;
    private Integer yearsOfExperience;
    private BigDecimal consultationFee;
    private String availableDays;
    private String availableHours;
    private String consultorio;
    private String universidad;
}
