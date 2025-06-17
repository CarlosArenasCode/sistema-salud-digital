package com.clinica.salud.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad que representa una receta médica en el sistema.
 * Relaciona medicamentos con pacientes y médicos.
 */
@Entity
@Table(name = "recetas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecetaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
      // ==================== RELACIONES ====================
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_paciente", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "recetas"})
    private PacienteEntity paciente;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_medico", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "recetas"})
    private MedicoEntity medico;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_medicamento", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "recetas"})
    private MedicamentoEntity medicamento;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_historial")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private HistorialMedicoEntity historialMedico;
    
    // ==================== DATOS DE LA RECETA ====================
    @NotBlank(message = "La dosis es obligatoria")
    @Column(name = "dosis", nullable = false)
    private String dosis;
    
    @NotBlank(message = "La frecuencia es obligatoria")
    @Column(name = "frecuencia", nullable = false)
    private String frecuencia;
    
    @NotNull(message = "La duración del tratamiento es obligatoria")
    @Column(name = "duracion_dias", nullable = false)
    private Integer duracionDias;
    
    @Column(name = "instrucciones_especiales", columnDefinition = "TEXT")
    private String instruccionesEspeciales;
    
    @Column(name = "cantidad_total")
    private Integer cantidadTotal;
    
    @NotNull(message = "La fecha de prescripción es obligatoria")
    @Column(name = "fecha_prescripcion", nullable = false)
    private LocalDate fechaPrescripcion;
    
    @Column(name = "fecha_vencimiento_receta")
    private LocalDate fechaVencimientoReceta;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    @Builder.Default
    private EstadoReceta estado = EstadoReceta.ACTIVA;
    
    // ==================== AUDITORÍA ====================
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
        if (fechaPrescripcion == null) {
            fechaPrescripcion = LocalDate.now();
        }
        if (fechaVencimientoReceta == null) {
            fechaVencimientoReceta = fechaPrescripcion.plusMonths(3); // Válida por 3 meses
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
    
    // ==================== MÉTODOS DE NEGOCIO ====================
    public boolean isVencida() {
        return fechaVencimientoReceta != null && fechaVencimientoReceta.isBefore(LocalDate.now());
    }
    
    public boolean isActiva() {
        return estado == EstadoReceta.ACTIVA && !isVencida();
    }
    
    // ==================== ENUM PARA ESTADO ====================
    public enum EstadoReceta {
        ACTIVA,
        DISPENSADA,
        PARCIALMENTE_DISPENSADA,
        VENCIDA,
        CANCELADA
    }
}
