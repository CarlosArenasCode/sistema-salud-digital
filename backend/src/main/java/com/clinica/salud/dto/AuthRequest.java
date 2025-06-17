package com.clinica.salud.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO para solicitudes de autenticación
 */
public class AuthRequest {

    // --------------------- ATRIBUTOS ------------------------
    /**
     * Propiedades para almacenar las credenciales del usuario
     */
    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    // --------------------- CONSTRUCTORES --------------------
    /**
     * Constructores para crear instancias de solicitud de autenticación
     */
    public AuthRequest() {}

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // ----------------- GETTERS Y SETTERS -------------------
    /**
     * Métodos de acceso para las propiedades del objeto
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
