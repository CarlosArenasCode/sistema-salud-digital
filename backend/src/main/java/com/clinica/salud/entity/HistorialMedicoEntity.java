package com.clinica.salud.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad que representa el historial médico de un paciente.
 * Mapea con la tabla 'historiales_medicos' en PostgreSQL.
 * Unificada y estandarizada en español.
 */
@Entity
@Table(name = "historiales_medicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class HistorialMedicoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "El ID del paciente es obligatorio")
    @Column(name = "id_paciente", nullable = false)
    private Long idPaciente;
    
    @NotNull(message = "El ID del médico es obligatorio")
    @Column(name = "id_medico", nullable = false)
    private Long idMedico;
    
    @Column(name = "id_cita")
    private Long idCita;
    
    @NotNull(message = "La fecha de visita es obligatoria")
    @Column(name = "fecha_visita", nullable = false)
    private LocalDate fechaVisita;
    
    @Column(name = "motivo_consulta", columnDefinition = "TEXT")
    private String motivoConsulta;
    
    @Column(name = "sintomas", columnDefinition = "TEXT")
    private String sintomas;
    
    @NotBlank(message = "El diagnóstico es obligatorio")
    @Column(name = "diagnostico", columnDefinition = "TEXT", nullable = false)
    private String diagnostico;
    
    @Column(name = "tratamiento", columnDefinition = "TEXT")
    private String tratamiento;
    
    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
    
    @Column(name = "medicamentos", columnDefinition = "TEXT")
    private String medicamentos;
    
    @Column(name = "examen_fisico", columnDefinition = "TEXT")
    private String examenFisico;
    
    @Column(name = "recomendaciones", columnDefinition = "TEXT")
    private String recomendaciones;
    
    @Column(name = "proxima_cita")
    private LocalDate proximaCita;
    
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    // Relaciones JPA
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", insertable = false, updatable = false)
    @JsonIgnore
    private PacienteEntity paciente;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_medico", insertable = false, updatable = false)
    @JsonIgnore
    private MedicoEntity medico;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cita", insertable = false, updatable = false)
    @JsonIgnore
    private CitaEntity cita;
    
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}
