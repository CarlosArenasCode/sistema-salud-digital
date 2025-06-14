package com.clinica.salud.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad que representa a un paciente en el sistema de salud digital.
 * Mapea con la tabla 'pacientes' en PostgreSQL.
 * Unificada y estandarizada en español.
 */
@Entity
@Table(name = "pacientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class PacienteEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "id_usuario")
    private Long idUsuario;
    
    @NotBlank(message = "Los nombres son obligatorios")
    @Size(max = 100, message = "Los nombres no pueden exceder 100 caracteres")
    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;
    
    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 100, message = "Los apellidos no pueden exceder 100 caracteres")
    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;
    
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;
    
    @Pattern(regexp = "MASCULINO|FEMENINO|OTRO", message = "El género debe ser MASCULINO, FEMENINO u OTRO")
    @Column(name = "genero", length = 10)
    private String genero;
      @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
    @Column(name = "telefono", length = 20)
    private String telefono;
    
    @Email(message = "Formato de email inválido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    @Column(name = "correo_electronico", length = 100)
    private String email;
    
    @Column(name = "direccion", columnDefinition = "TEXT")
    private String direccion;
    
    @Size(max = 100, message = "El contacto de emergencia no puede exceder 100 caracteres")
    @Column(name = "contacto_emergencia", length = 100)
    private String contactoEmergencia;
    
    @Size(max = 20, message = "El teléfono de emergencia no puede exceder 20 caracteres")
    @Column(name = "telefono_emergencia", length = 20)
    private String telefonoEmergencia;
    
    @Size(max = 5, message = "El tipo de sangre no puede exceder 5 caracteres")
    @Column(name = "tipo_sangre", length = 5)
    private String tipoSangre;
    
    @Column(name = "alergias", columnDefinition = "TEXT")
    private String alergias;
    
    @Size(max = 100, message = "El seguro médico no puede exceder 100 caracteres")
    @Column(name = "seguro_medico", length = 100)
    private String seguroMedico;
    
    @Size(max = 20, message = "El número de identificación no puede exceder 20 caracteres")
    @Column(name = "numero_identificacion", unique = true, length = 20)
    private String numeroIdentificacion;
    
    @Size(max = 20, message = "El estado civil no puede exceder 20 caracteres")
    @Column(name = "estado_civil", length = 20)
    private String estadoCivil;
    
    @Size(max = 100, message = "La ocupación no puede exceder 100 caracteres")
    @Column(name = "ocupacion", length = 100)
    private String ocupacion;
    
    @Builder.Default
    @Column(name = "activo")
    private Boolean activo = true;
    
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    // Relación con usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    private UsuarioEntity usuario;
    
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
    
    // Método utilitario para obtener el nombre completo
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
    
    // Método para calcular la edad
    public int getEdad() {
        if (fechaNacimiento != null) {
            return LocalDate.now().getYear() - fechaNacimiento.getYear();
        }
        return 0;
    }
}
