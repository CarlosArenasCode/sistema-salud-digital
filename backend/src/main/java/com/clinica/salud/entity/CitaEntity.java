package com.clinica.salud.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Entidad que representa una cita médica en el sistema de salud digital.
 * Mapea con la tabla 'citas' en PostgreSQL.
 * OPTIMIZADA: Reducido de 340 líneas a ~90 líneas usando Lombok y BaseEntity
 */
@Entity
@Table(name = "citas", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"id_medico", "fecha_cita", "hora_cita"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class CitaEntity extends BaseEntity {
    
    @NotNull(message = "El ID del paciente es obligatorio")
    @Column(name = "id_paciente", nullable = false)
    private Long idPaciente;
    
    @NotNull(message = "El ID del médico es obligatorio")
    @Column(name = "id_medico", nullable = false)
    private Long idMedico;
    
    @NotNull(message = "La fecha de la cita es obligatoria")
    @Column(name = "fecha_cita", nullable = false)
    private LocalDate fechaCita;
    
    @NotNull(message = "La hora de la cita es obligatoria")
    @Column(name = "hora_cita", nullable = false)
    private LocalTime horaCita;
    
    @Size(max = 20, message = "El estado no puede exceder 20 caracteres")
    @Builder.Default
    @Column(name = "estado", length = 20)
    private String estado = "PROGRAMADA";
    
    @Column(name = "motivo", columnDefinition = "TEXT")
    private String motivo;
    
    @Column(name = "notas", columnDefinition = "TEXT")
    private String notas;
    
    @Min(value = 15, message = "La duración mínima es 15 minutos")
    @Builder.Default
    @Column(name = "duracion_minutos")
    private Integer duracionMinutos = 30;
    
    @Size(max = 50, message = "El tipo de consulta no puede exceder 50 caracteres")
    @Builder.Default
    @Column(name = "tipo_consulta", length = 50)
    private String tipoConsulta = "GENERAL";
    
    // Relaciones JPA
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", insertable = false, updatable = false)
    @JsonIgnore
    private PacienteEntity paciente;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_medico", insertable = false, updatable = false)
    @JsonIgnore
    private MedicoEntity medico;
    
    // Métodos de compatibilidad (deprecados para transición gradual)
    @Deprecated
    public Long getPatientId() {
        return this.idPaciente;
    }
    
    @Deprecated
    public void setPatientId(Long patientId) {
        this.idPaciente = patientId;
    }
    
    @Deprecated
    public Long getDoctorId() {
        return this.idMedico;
    }
    
    @Deprecated
    public void setDoctorId(Long doctorId) {
        this.idMedico = doctorId;
    }
}
