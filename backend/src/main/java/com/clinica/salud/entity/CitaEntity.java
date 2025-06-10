package com.clinica.salud.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

/**
 * Entidad que representa una cita médica en el sistema de salud digital.
 * Mapea con la tabla 'citas' en PostgreSQL.
 * Unificada y estandarizada en español.
 */
@Entity
@Table(name = "citas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class CitaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "El ID del paciente es obligatorio")
    @Column(name = "id_paciente", nullable = false)
    private Long idPaciente;
    
    @NotNull(message = "El ID del médico es obligatorio")
    @Column(name = "id_medico", nullable = false)
    private Long idMedico;
    
    @NotNull(message = "La fecha de la cita es obligatoria")
    @Column(name = "fecha_cita", nullable = false)
    private LocalDateTime fechaCita;
    
    @Column(name = "motivo_consulta", columnDefinition = "TEXT")
    private String motivoConsulta;
    
    @Size(max = 20, message = "El estado no puede exceder 20 caracteres")
    @Builder.Default
    @Column(name = "estado", length = 20)
    private String estado = "PROGRAMADA";
    
    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
    
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
