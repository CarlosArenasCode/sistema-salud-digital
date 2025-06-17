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
    
    // ======== IDENTIFICADORES ========
    /**
     * Identificador único del historial médico
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Referencias a entidades relacionadas
     */
    @NotNull(message = "El ID del paciente es obligatorio")
    @Column(name = "id_paciente", nullable = false)
    private Long idPaciente;
    
    @NotNull(message = "El ID del médico es obligatorio")
    @Column(name = "id_medico", nullable = false)
    private Long idMedico;
    
    @Column(name = "id_cita")
    private Long idCita;
    
    // ======== INFORMACIÓN DE LA CONSULTA ========
    /**
     * Datos generales de la visita médica
     */
    @NotNull(message = "La fecha de visita es obligatoria")
    @Column(name = "fecha_visita", nullable = false)
    private LocalDate fechaVisita;
    
    @Column(name = "motivo_consulta", columnDefinition = "TEXT")
    private String motivoConsulta;
    
    @Column(name = "sintomas", columnDefinition = "TEXT")
    private String sintomas;
    
    // ======== DIAGNÓSTICO Y TRATAMIENTO ========
    /**
     * Información clínica y plan de tratamiento
     */
    @NotBlank(message = "El diagnóstico es obligatorio")
    @Column(name = "diagnostico", columnDefinition = "TEXT", nullable = false)
    private String diagnostico;
    
    @Column(name = "tratamiento", columnDefinition = "TEXT")
    private String tratamiento;
    
    @Column(name = "medicamentos", columnDefinition = "TEXT")
    private String medicamentos;
    
    @Column(name = "examen_fisico", columnDefinition = "TEXT")
    private String examenFisico;
    
    // ======== SEGUIMIENTO Y OBSERVACIONES ========
    /**
     * Datos de seguimiento y recomendaciones
     */
    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
    
    @Column(name = "recomendaciones", columnDefinition = "TEXT")
    private String recomendaciones;
    
    @Column(name = "proxima_cita")
    private LocalDate proximaCita;
    
    // ======== METADATOS ========
    /**
     * Información de auditoría
     */
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    // ======== RELACIONES ========
    /**
     * Relaciones con otras entidades del sistema
     */
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
    
    // ======== MÉTODOS DEL CICLO DE VIDA ========
    /**
     * Métodos automáticos para gestión de fechas
     */
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
