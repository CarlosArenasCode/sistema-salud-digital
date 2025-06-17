package com.clinica.salud.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entidad que representa los resultados de laboratorio de un paciente
 */
@Entity
@Table(name = "resultados_laboratorio")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultadoLaboratorioEntity extends BaseEntity {
    
    // ------------------------------
    // Identificador principal
    // ------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resultado")
    private Long id;
    
    // ------------------------------
    // Datos básicos del resultado
    // ------------------------------
    @Column(name = "id_paciente", nullable = false)
    private Long idPaciente;
    
    @NotBlank(message = "El tipo de examen es obligatorio")
    @Column(name = "tipo_examen", nullable = false, length = 100)
    private String tipoExamen;
    
    @NotBlank(message = "El resultado es obligatorio")
    @Column(name = "resultado", nullable = false, columnDefinition = "TEXT")
    private String resultado;
    
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;
    
    // ------------------------------
    // Relaciones con otras entidades
    // ------------------------------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", insertable = false, updatable = false)
    private PacienteEntity paciente;
}
