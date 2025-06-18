package com.clinica.salud.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

// Entidad JPA que mapea la tabla 'medicos' en PostgreSQL
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
    // Clave primaria autoincremental
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Referencia al usuario asociado con el médico
    @Column(name = "id_usuario")
    private Long idUsuario;
      //--------------------------------------------------------------------------
    // DATOS PERSONALES
    //--------------------------------------------------------------------------
    // Nombres del médico (obligatorio, máx 100 caracteres)
    @NotBlank(message = "Los nombres son obligatorios")
    @Size(max = 100, message = "Los nombres no pueden exceder 100 caracteres")
    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;
    
    // Apellidos del médico (obligatorio, máx 100 caracteres)
    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 100, message = "Los apellidos no pueden exceder 100 caracteres")
    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;
    
    // Número telefónico del médico (opcional, máx 20 caracteres)
    @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
    @Column(name = "telefono", length = 20)
    private String telefono;
    
    // Correo electrónico del médico (validado, máx 100 caracteres)
    @Email(message = "Formato de email inválido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    @Column(name = "email", length = 100)
    private String email;
      //--------------------------------------------------------------------------
    // INFORMACIÓN PROFESIONAL
    //--------------------------------------------------------------------------
    // Área de especialización médica (obligatorio, máx 100 caracteres)
    @NotBlank(message = "La especialización es obligatoria")
    @Size(max = 100, message = "La especialización no puede exceder 100 caracteres")
    @Column(name = "especializacion", nullable = false, length = 100)
    private String especializacion;
    
    // Número de licencia médica único (obligatorio, máx 50 caracteres)
    @NotBlank(message = "El número de licencia es obligatorio")
    @Size(max = 50, message = "El número de licencia no puede exceder 50 caracteres")
    @Column(name = "numero_licencia", unique = true, nullable = false, length = 50)
    private String numeroLicencia;
    
    // Años de experiencia profesional (no negativo)
    @Min(value = 0, message = "Los años de experiencia no pueden ser negativos")
    @Column(name = "anos_experiencia")
    private Integer anosExperiencia;
    
    // Universidad donde se graduó (opcional, máx 100 caracteres)
    @Size(max = 100, message = "La universidad no puede exceder 100 caracteres")
    @Column(name = "universidad", length = 100)
    private String universidad;
    
    // Fecha de graduación universitaria
    @Column(name = "fecha_graduacion")
    private LocalDate fechaGraduacion;
      //--------------------------------------------------------------------------
    // INFORMACIÓN DE CONSULTA Y DISPONIBILIDAD
    //--------------------------------------------------------------------------
    // Costo por consulta médica (no negativo, hasta 10 dígitos con 2 decimales)
    @DecimalMin(value = "0.0", message = "La tarifa de consulta no puede ser negativa")
    @Column(name = "tarifa_consulta", precision = 10, scale = 2)
    private BigDecimal tarifaConsulta;
    
    // Días de la semana disponibles para consultas (máx 100 caracteres)
    @Size(max = 100, message = "Los días disponibles no pueden exceder 100 caracteres")
    @Column(name = "dias_disponibles", length = 100)
    private String diasDisponibles;
    
    // Horarios de atención disponibles (máx 100 caracteres)
    @Size(max = 100, message = "Las horas disponibles no pueden exceder 100 caracteres")
    @Column(name = "horas_disponibles", length = 100)
    private String horasDisponibles;
    
    // Número o ubicación del consultorio (máx 50 caracteres)
    @Size(max = 50, message = "El consultorio no puede exceder 50 caracteres")
    @Column(name = "consultorio", length = 50)
    private String consultorio;
      //--------------------------------------------------------------------------
    // DATOS DE CONTROL
    //--------------------------------------------------------------------------
    // Estado del médico en el sistema (activo por defecto)
    @Builder.Default
    @Column(name = "activo")
    private Boolean activo = true;
    
    // Fecha y hora de creación del registro (automática, no actualizable)
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    // Fecha y hora de última actualización del registro
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;      //--------------------------------------------------------------------------
    // RELACIONES
    //--------------------------------------------------------------------------    
    // Relación con UsuarioEntity (carga eager, excluye contraseña en JSON)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "contrasena"})
    private UsuarioEntity usuario;
      //--------------------------------------------------------------------------
    // MÉTODOS DE CICLO DE VIDA
    //--------------------------------------------------------------------------
    // Ejecutado automáticamente antes de persistir la entidad
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }
    
    // Ejecutado automáticamente antes de actualizar la entidad
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
      //--------------------------------------------------------------------------
    // MÉTODOS UTILITARIOS
    //--------------------------------------------------------------------------
    // Concatena nombres y apellidos para obtener el nombre completo
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
}
