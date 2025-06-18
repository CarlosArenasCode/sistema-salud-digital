package com.clinica.salud.dto;

import jakarta.validation.constraints.NotBlank;

// DTO para solicitudes de autenticación de usuarios
public class AuthRequest {
    // Nombre de usuario requerido para autenticación
    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String username;

    // Contraseña requerida para autenticación
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;    // Constructor por defecto
    public AuthRequest() {}

    // Constructor con parámetros para credenciales
    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;    }

    // Getter para obtener el nombre de usuario
    public String getUsername() {
        return username;    }

    // Setter para establecer el nombre de usuario
    public void setUsername(String username) {
        this.username = username;    }

    // Getter para obtener la contraseña
    public String getPassword() {
        return password;    }

    // Setter para establecer la contraseña
    public void setPassword(String password) {
        this.password = password;
    }
}
