package com.clinica.salud.dto;

import java.util.Map;

// DTO para respuestas de autenticación con JWT
public class AuthResponse {    // Token JWT generado para la sesión
    private String token;
    // Información del usuario autenticado
    private Map<String, Object> user;
    // Mensaje de respuesta opcional
    private String message;    // Constructor por defecto
    public AuthResponse() {}

    // Constructor con token y datos de usuario
    public AuthResponse(String token, Map<String, Object> user) {
        this.token = token;
        this.user = user;    }

    // Constructor completo con token, usuario y mensaje
    public AuthResponse(String token, Map<String, Object> user, String message) {
        this.token = token;
        this.user = user;
        this.message = message;    }

    // Getter para obtener el token JWT
    public String getToken() {
        return token;    }

    // Setter para establecer el token JWT
    public void setToken(String token) {
        this.token = token;    }

    // Getter para obtener información del usuario
    public Map<String, Object> getUser() {
        return user;    }

    // Setter para establecer información del usuario
    public void setUser(Map<String, Object> user) {
        this.user = user;    }

    // Getter para obtener mensaje de respuesta
    public String getMessage() {
        return message;    }

    // Setter para establecer mensaje de respuesta
    public void setMessage(String message) {
        this.message = message;
    }
}
