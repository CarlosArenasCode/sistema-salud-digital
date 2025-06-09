package com.clinica.salud.modelo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
@Schema(description = "Modelo que representa un usuario del sistema")
public class Usuario {
    @Schema(description = "ID del usuario", example = "1")
    private int idUsuario;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Schema(description = "Nombre de usuario", example = "usuario_prueba")
    private String nombreUsuario;

    @Email(message = "El email debe tener un formato válido")
    @Schema(description = "Email del usuario", example = "usuario@ejemplo.com")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Schema(description = "Contraseña del usuario", example = "password123")
    private String password;

    @Schema(description = "Rol del usuario", example = "ADMINISTRADOR")
    private String role;

    private java.time.LocalDateTime createdAt;

    private java.time.LocalDateTime updatedAt;

    public Usuario() {}

    public Usuario(int idUsuario, String nombreUsuario, String email, String password, String role) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters y Setters
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public java.time.LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(java.time.LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public java.time.LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(java.time.LocalDateTime updatedAt) { this.updatedAt = updatedAt; }    // Alias methods for compatibility
    public String getContrasena() { return password; }
    public void setContrasena(String contrasena) { this.password = contrasena; }
    
    public String getRol() { return role; }
    public void setRol(String role) { this.role = role; }
    
    // Métodos para trabajar con el enum Rol
    public void setRol(Rol rol) { 
        this.role = rol != null ? rol.getNombreRol() : null; 
    }
    
    public Rol getRolEnum() { 
        return role != null ? Rol.fromString(role) : null; 
    }
      // Legacy compatibility for int-based role ID
    public int getIdRol() { 
        // Convert string role to int for legacy compatibility
        switch (role != null ? role : "") {
            case "ADMINISTRADOR": return 1;
            case "MEDICO": return 2;
            case "PACIENTE": return 3;
            default: return 0;
        }
    }
    
    public void setIdRol(int idRol) {
        // Convert int to string role
        switch (idRol) {
            case 1: this.role = "ADMINISTRADOR"; break;
            case 2: this.role = "MEDICO"; break;
            case 3: this.role = "PACIENTE"; break;
            default: this.role = "PACIENTE"; break;
        }
    }
}
