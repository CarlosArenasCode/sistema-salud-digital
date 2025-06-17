package com.clinica.salud.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad que representa a un médico en el sistema de salud digital.
 * Mapea con la tabla 'medicos' en PostgreSQL.
 * Unificada y estandarizada en español.
 */
@Entity
@Table(name = "medicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class MedicoEntity {
    
    //--------------------------------------------------------------------------
    // IDENTIFICADORES
    //--------------------------------------------------------------------------
    /** Identificadores únicos para el médico */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "id_usuario")
    private Long idUsuario;
    
    //--------------------------------------------------------------------------
    // DATOS PERSONALES
    //--------------------------------------------------------------------------
    /** Información personal básica del médico */
    @NotBlank(message = "Los nombres son obligatorios")
    @Size(max = 100, message = "Los nombres no pueden exceder 100 caracteres")
    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;
    
    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 100, message = "Los apellidos no pueden exceder 100 caracteres")
    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;
    
    @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
    @Column(name = "telefono", length = 20)
    private String telefono;
    
    @Email(message = "Formato de email inválido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    @Column(name = "email", length = 100)
    private String email;
    
    //--------------------------------------------------------------------------
    // INFORMACIÓN PROFESIONAL
    //--------------------------------------------------------------------------
    /** Datos relacionados con la profesión médica */
    @NotBlank(message = "La especialización es obligatoria")
    @Size(max = 100, message = "La especialización no puede exceder 100 caracteres")
    @Column(name = "especializacion", nullable = false, length = 100)
    private String especializacion;
    
    @NotBlank(message = "El número de licencia es obligatorio")
    @Size(max = 50, message = "El número de licencia no puede exceder 50 caracteres")
    @Column(name = "numero_licencia", unique = true, nullable = false, length = 50)
    private String numeroLicencia;
    
    @Min(value = 0, message = "Los años de experiencia no pueden ser negativos")
    @Column(name = "anos_experiencia")
    private Integer anosExperiencia;
    
    @Size(max = 100, message = "La universidad no puede exceder 100 caracteres")
    @Column(name = "universidad", length = 100)
    private String universidad;
    
    @Column(name = "fecha_graduacion")
    private LocalDate fechaGraduacion;
    
    //--------------------------------------------------------------------------
    // INFORMACIÓN DE CONSULTA Y DISPONIBILIDAD
    //--------------------------------------------------------------------------
    /** Datos sobre disponibilidad del médico y atención a pacientes */
    @DecimalMin(value = "0.0", message = "La tarifa de consulta no puede ser negativa")
    @Column(name = "tarifa_consulta", precision = 10, scale = 2)
    private BigDecimal tarifaConsulta;
    
    @Size(max = 100, message = "Los días disponibles no pueden exceder 100 caracteres")
    @Column(name = "dias_disponibles", length = 100)
    private String diasDisponibles;
    
    @Size(max = 100, message = "Las horas disponibles no pueden exceder 100 caracteres")
    @Column(name = "horas_disponibles", length = 100)
    private String horasDisponibles;
    
    @Size(max = 50, message = "El consultorio no puede exceder 50 caracteres")
    @Column(name = "consultorio", length = 50)
    private String consultorio;
    
    //--------------------------------------------------------------------------
    // DATOS DE CONTROL
    //--------------------------------------------------------------------------
    /** Información de estado y auditoría */
    @Builder.Default
    @Column(name = "activo")
    private Boolean activo = true;
    
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
      //--------------------------------------------------------------------------
    // RELACIONES
    //--------------------------------------------------------------------------    /** Relaciones con otras entidades del sistema */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "contrasena"})
    private UsuarioEntity usuario;
    
    //--------------------------------------------------------------------------
    // MÉTODOS DE CICLO DE VIDA
    //--------------------------------------------------------------------------
    /** Métodos automáticos para gestionar fechas de creación y actualización */
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
    
    //--------------------------------------------------------------------------
    // MÉTODOS UTILITARIOS
    //--------------------------------------------------------------------------
    /** Métodos de conveniencia para operaciones comunes */
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
}
