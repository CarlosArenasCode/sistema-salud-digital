package com.clinica.salud.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.time.LocalDateTime;

// Entidad JPA que representa recetas médicas con relaciones a paciente, médico y medicamento
@Entity
@Table(name = "recetas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecetaEntity {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único de la receta
      
    // Relaciones
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_paciente", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "recetas"})
    private PacienteEntity paciente; // Paciente al que se prescribe la receta
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_medico", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "recetas"})
    private MedicoEntity medico; // Médico que prescribe la receta
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_medicamento", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "recetas"})
    private MedicamentoEntity medicamento; // Medicamento prescrito
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_historial")    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private HistorialMedicoEntity historialMedico; // Historial médico asociado (opcional)
    
    // Datos de la Receta
    @NotBlank(message = "La dosis es obligatoria")
    @Column(name = "dosis", nullable = false)
    private String dosis; // Cantidad de medicamento por toma
    
    @NotBlank(message = "La frecuencia es obligatoria")
    @Column(name = "frecuencia", nullable = false)
    private String frecuencia; // Frecuencia de administración del medicamento
    
    @NotNull(message = "La duración del tratamiento es obligatoria")
    @Column(name = "duracion_dias", nullable = false)
    private Integer duracionDias; // Duración del tratamiento en días
    
    @Column(name = "instrucciones_especiales", columnDefinition = "TEXT")
    private String instruccionesEspeciales; // Instrucciones adicionales para el paciente
    
    @Column(name = "cantidad_total")
    private Integer cantidadTotal; // Cantidad total de medicamento a dispensar
    
    @NotNull(message = "La fecha de prescripción es obligatoria")
    @Column(name = "fecha_prescripcion", nullable = false)
    private LocalDate fechaPrescripcion; // Fecha cuando se prescribió la receta
    
    @Column(name = "fecha_vencimiento_receta")
    private LocalDate fechaVencimientoReceta; // Fecha hasta cuando es válida la receta
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    @Builder.Default    private EstadoReceta estado = EstadoReceta.ACTIVA; // Estado actual de la receta
    
    // Auditoria
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion; // Timestamp de creación del registro
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion; // Timestamp de última actualización
    
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
    protected void onUpdate() {        fechaActualizacion = LocalDateTime.now(); // Actualiza timestamp en cada modificación
    }
    
    // Metodos de Negocio
    // Verifica si la receta ha vencido según su fecha de vencimiento
    public boolean isVencida() {
        return fechaVencimientoReceta != null && fechaVencimientoReceta.isBefore(LocalDate.now());
    }
    
    // Verifica si la receta está activa y no ha vencido
    public boolean isActiva() {        return estado == EstadoReceta.ACTIVA && !isVencida();
    }
    
    // Enum para Estado
    public enum EstadoReceta {
        ACTIVA, // Receta válida y no dispensada
        DISPENSADA, // Receta completamente dispensada
        PARCIALMENTE_DISPENSADA, // Receta parcialmente dispensada
        VENCIDA, // Receta fuera de su período de validez
        CANCELADA // Receta cancelada por el médico
    }
}
