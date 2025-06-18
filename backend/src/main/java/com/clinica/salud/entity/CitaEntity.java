package com.clinica.salud.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

// Entidad JPA que representa una cita médica en la tabla 'citas'
@Entity
@Table(name = "citas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class CitaEntity {    
    // Identificador único autogenerado de la cita
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // ID del paciente asociado a la cita (requerido)
    @NotNull(message = "El ID del paciente es obligatorio")
    @Column(name = "id_paciente", nullable = false)
    private Long idPaciente;
    
    // ID del médico asignado a la cita (requerido)
    @NotNull(message = "El ID del médico es obligatorio")
    @Column(name = "id_medico", nullable = false)
    private Long idMedico;
    
    // Fecha y hora programada para la cita (requerida)
    @NotNull(message = "La fecha de la cita es obligatoria")
    @Column(name = "fecha_cita", nullable = false)
    private LocalDateTime fechaCita;
    
    // Motivo o razón de la consulta médica
    @Column(name = "motivo_consulta", columnDefinition = "TEXT")
    private String motivoConsulta;
    
    // Estado actual de la cita (por defecto PROGRAMADA)
    @Size(max = 20, message = "El estado no puede exceder 20 caracteres")
    @Builder.Default
    @Column(name = "estado", length = 20)
    private String estado = "PROGRAMADA";
    
    // Observaciones adicionales de la cita
    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;    
    // Fecha y hora de creación del registro (inmutable)
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    // Fecha y hora de última actualización del registro
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;    
    
    // Relación Many-to-One con la entidad Paciente (carga perezosa)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", insertable = false, updatable = false)
    @JsonIgnore
    private PacienteEntity paciente;
    
    // Relación Many-to-One con la entidad Medico (carga perezosa)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_medico", insertable = false, updatable = false)
    @JsonIgnore
    private MedicoEntity medico;    
    // Método ejecutado automáticamente antes de persistir la entidad
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }
    
    // Método ejecutado automáticamente antes de actualizar la entidad
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}
