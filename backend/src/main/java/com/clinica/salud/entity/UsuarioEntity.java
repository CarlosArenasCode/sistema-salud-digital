package com.clinica.salud.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Entidad que representa a un usuario en el sistema de salud digital.
 * Mapea con la tabla 'usuarios' en PostgreSQL.
 * Unificada y estandarizada en español.
 * Incluye campos para autenticación, autorización y auditoría.
 */
@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioEntity {
  
  // Identificador único del usuario en el sistema
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // Datos principales que identifican al usuario en el sistema
  @NotBlank(message = "El nombre de usuario es obligatorio")
  @Size(max = 50, message = "El nombre de usuario no puede exceder 50 caracteres")
  @Column(name = "nombre_usuario", unique = true, nullable = false, length = 50)
  private String nombreUsuario;
  
  @Email(message = "Formato de email inválido")
  @NotBlank(message = "El email es obligatorio")
  @Size(max = 100, message = "El email no puede exceder 100 caracteres")
  @Column(name = "correo_electronico", unique = true, nullable = false, length = 100)
  private String email;

  // Datos relacionados con la autenticación y autorización del usuario
  @NotBlank(message = "La contraseña es obligatoria")
  @Size(max = 255, message = "La contraseña no puede exceder 255 caracteres")
  @Column(name = "contrasena", nullable = false)
  private String contrasena;
  
  @NotBlank(message = "El rol es obligatorio")
  @Pattern(regexp = "ADMINISTRADOR|MEDICO|PACIENTE|ENFERMERO", message = "El rol debe ser uno de: ADMINISTRADOR, MEDICO, PACIENTE, ENFERMERO")
  @Column(name = "rol", nullable = false, length = 20)
  private String rol;
  
  // Campos para el seguimiento y control del estado de la cuenta
  @Builder.Default
  @Column(name = "activo")
  private Boolean activo = true;
  
  @Column(name = "fecha_creacion", nullable = false, updatable = false)
  private LocalDateTime fechaCreacion;
  
  @Column(name = "fecha_actualizacion")
  private LocalDateTime fechaActualizacion;

  // Callbacks que se ejecutan automáticamente durante operaciones de persistencia
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
