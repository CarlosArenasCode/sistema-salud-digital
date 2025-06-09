package com.clinica.salud.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad que representa a un médico en el sistema de salud digital.
 * Mapea con la tabla 'medicos' en PostgreSQL.
 * Optimizada con Lombok para reducir código boilerplate.
 */
@Entity
@Table(name = "medicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class MedicoEntity extends BaseEntity {
    
    @Column(name = "id_usuario")
    private Long userId;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    @Column(name = "nombres", nullable = false, length = 100)
    private String firstName;
    
    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "El apellido no puede exceder 100 caracteres")
    @Column(name = "apellidos", nullable = false, length = 100)
    private String lastName;
      @NotBlank(message = "La especialidad es obligatoria")
    @Size(max = 100, message = "La especialidad no puede exceder 100 caracteres")
    @Column(name = "especializacion", nullable = false, length = 100)
    private String specialization;
    
    @Size(max = 50, message = "El número de licencia no puede exceder 50 caracteres")
    @Column(name = "numero_licencia", unique = true, length = 50)
    private String licenseNumber;
    
    @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
    @Column(name = "telefono", length = 20)
    private String phone;
    
    @Email(message = "Formato de email inválido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    @Column(name = "email", length = 100)
    private String email;
    
    @Min(value = 0, message = "Los años de experiencia no pueden ser negativos")
    @Column(name = "anos_experiencia")
    private Integer yearsOfExperience;
    
    @DecimalMin(value = "0.0", message = "La tarifa de consulta no puede ser negativa")
    @Column(name = "tarifa_consulta", precision = 10, scale = 2)
    private BigDecimal consultationFee;
    
    @Size(max = 100, message = "Los días disponibles no pueden exceder 100 caracteres")
    @Column(name = "dias_disponibles", length = 100)
    private String availableDays;
    
    @Size(max = 100, message = "Las horas disponibles no pueden exceder 100 caracteres")
    @Column(name = "horas_disponibles", length = 100)
    private String availableHours;
    
    @Size(max = 50, message = "El consultorio no puede exceder 50 caracteres")
    @Column(name = "consultorio", length = 50)
    private String consultorio;
    
    @Size(max = 100, message = "La universidad no puede exceder 100 caracteres")
    @Column(name = "universidad", length = 100)
    private String universidad;
    
    @Column(name = "fecha_graduacion")
    private LocalDate fechaGraduacion;
    
    @Builder.Default
    @Column(name = "activo")
    private Boolean activo = true;
    
    // Método utilitario para obtener el nombre completo
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
