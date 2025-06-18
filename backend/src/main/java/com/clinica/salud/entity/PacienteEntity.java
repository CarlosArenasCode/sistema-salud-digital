package com.clinica.salud.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.time.LocalDateTime;

// Entidad JPA que representa un paciente en el sistema de salud digital
@Entity
@Table(name = "pacientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class PacienteEntity {
    
    // ==================== IDENTIFICACIÓN ====================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único del paciente
    
    @Column(name = "id_usuario")
    private Long idUsuario; // Referencia al usuario asociado
    
    // ==================== INFORMACIÓN PERSONAL BÁSICA ====================
    @NotBlank(message = "Los nombres son obligatorios")
    @Size(max = 100, message = "Los nombres no pueden exceder 100 caracteres")
    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres; // Nombres del paciente
    
    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 100, message = "Los apellidos no pueden exceder 100 caracteres")
    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos; // Apellidos del paciente
    
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento; // Fecha de nacimiento del paciente
    
    @Pattern(regexp = "MASCULINO|FEMENINO|OTRO", message = "El género debe ser MASCULINO, FEMENINO u OTRO")
    @Column(name = "genero", length = 10)
    private String genero; // Género del paciente
    
    // ==================== INFORMACIÓN DE CONTACTO ====================
    @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
    @Column(name = "telefono", length = 20)
    private String telefono; // Número de teléfono principal
    
    @Email(message = "Formato de email inválido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    @Column(name = "correo_electronico", length = 100)
    private String email; // Correo electrónico del paciente
    
    @Column(name = "direccion", columnDefinition = "TEXT")
    private String direccion; // Dirección de residencia
    
    // ==================== CONTACTO DE EMERGENCIA ====================
    @Size(max = 100, message = "El contacto de emergencia no puede exceder 100 caracteres")
    @Column(name = "contacto_emergencia", length = 100)
    private String contactoEmergencia; // Nombre del contacto de emergencia
    
    @Size(max = 20, message = "El teléfono de emergencia no puede exceder 20 caracteres")
    @Column(name = "telefono_emergencia", length = 20)
    private String telefonoEmergencia; // Teléfono del contacto de emergencia
    
    // ==================== INFORMACIÓN MÉDICA ====================
    @Size(max = 5, message = "El tipo de sangre no puede exceder 5 caracteres")
    @Column(name = "tipo_sangre", length = 5)
    private String tipoSangre; // Tipo de sangre del paciente
    
    @Column(name = "alergias", columnDefinition = "TEXT")
    private String alergias; // Alergias conocidas del paciente
    
    @Size(max = 100, message = "El seguro médico no puede exceder 100 caracteres")
    @Column(name = "seguro_medico", length = 100)
    private String seguroMedico; // Información del seguro médico
    
    // ==================== INFORMACIÓN SOCIODEMOGRÁFICA ====================
    @Size(max = 20, message = "El número de identificación no puede exceder 20 caracteres")
    @Column(name = "numero_identificacion", unique = true, length = 20)
    private String numeroIdentificacion; // Documento de identidad único
    
    @Size(max = 20, message = "El estado civil no puede exceder 20 caracteres")
    @Column(name = "estado_civil", length = 20)
    private String estadoCivil; // Estado civil del paciente
    
    @Size(max = 100, message = "La ocupación no puede exceder 100 caracteres")
    @Column(name = "ocupacion", length = 100)
    private String ocupacion; // Ocupación laboral del paciente
    
    // ==================== CONTROL DE ESTADO ====================
    @Builder.Default
    @Column(name = "activo")
    private Boolean activo = true; // Estado activo/inactivo del paciente
    
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion; // Fecha de creación del registro
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion; // Fecha de última actualización
    
    // ==================== RELACIONES ====================
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "contrasena"})
    private UsuarioEntity usuario; // Relación con la entidad Usuario
    
    // ==================== MÉTODOS DE CICLO DE VIDA ====================
    @PrePersist
    protected void onCreate() { // Establece fechas de creación y actualización al crear
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() { // Actualiza la fecha de modificación
        fechaActualizacion = LocalDateTime.now();
    }
    
    // ==================== MÉTODOS UTILITARIOS ====================
    public String getNombreCompleto() { // Retorna el nombre completo concatenado
        return nombres + " " + apellidos;
    }
    
    public int getEdad() { // Calcula la edad en años basada en la fecha de nacimiento
        if (fechaNacimiento != null) {
            return LocalDate.now().getYear() - fechaNacimiento.getYear();
        }
        return 0;
    }
}
