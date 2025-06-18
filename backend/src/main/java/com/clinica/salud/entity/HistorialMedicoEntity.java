package com.clinica.salud.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.time.LocalDateTime;

// Entidad JPA que representa el historial médico en la tabla 'historiales_medicos'
@Entity
@Table(name = "historiales_medicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class HistorialMedicoEntity {    
    // Identificador único autogenerado del historial médico
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // ID del paciente asociado al historial (requerido)
    @NotNull(message = "El ID del paciente es obligatorio")
    @Column(name = "id_paciente", nullable = false)
    private Long idPaciente;
    
    // ID del médico que atendió la consulta (requerido)
    @NotNull(message = "El ID del médico es obligatorio")
    @Column(name = "id_medico", nullable = false)
    private Long idMedico;
    
    // ID de la cita asociada (opcional)
    @Column(name = "id_cita")
    private Long idCita;    
    // Fecha de la visita médica (requerida)
    @NotNull(message = "La fecha de visita es obligatoria")
    @Column(name = "fecha_visita", nullable = false)
    private LocalDate fechaVisita;
    
    // Motivo por el cual el paciente consultó
    @Column(name = "motivo_consulta", columnDefinition = "TEXT")
    private String motivoConsulta;
    
    // Síntomas presentados por el paciente
    @Column(name = "sintomas", columnDefinition = "TEXT")
    private String sintomas;    
    // Diagnóstico médico realizado (requerido)
    @NotBlank(message = "El diagnóstico es obligatorio")
    @Column(name = "diagnostico", columnDefinition = "TEXT", nullable = false)
    private String diagnostico;
    
    // Tratamiento prescrito al paciente
    @Column(name = "tratamiento", columnDefinition = "TEXT")
    private String tratamiento;
    
    // Medicamentos recetados durante la consulta
    @Column(name = "medicamentos", columnDefinition = "TEXT")
    private String medicamentos;
    
    // Resultados del examen físico realizado
    @Column(name = "examen_fisico", columnDefinition = "TEXT")
    private String examenFisico;    
    // Observaciones adicionales del médico
    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
    
    // Recomendaciones médicas para el paciente
    @Column(name = "recomendaciones", columnDefinition = "TEXT")
    private String recomendaciones;
    
    // Fecha sugerida para la próxima cita
    @Column(name = "proxima_cita")
    private LocalDate proximaCita;    
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
    
    // Relación Many-to-One con la entidad Cita (carga perezosa)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cita", insertable = false, updatable = false)
    @JsonIgnore
    private CitaEntity cita;    
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
