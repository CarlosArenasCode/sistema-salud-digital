package com.clinica.salud.dto;

import java.util.Map;

/**
 * DTO para respuestas de autenticación con JWT
 */
public class AuthResponse {

    //---------------------------------------------------------------------
    // Atributos
    //---------------------------------------------------------------------
    private String token;
    private Map<String, Object> user;
    private String message;

    //---------------------------------------------------------------------
    // Constructores
    //---------------------------------------------------------------------
    public AuthResponse() {}

    public AuthResponse(String token, Map<String, Object> user) {
        this.token = token;
        this.user = user;
    }

    public AuthResponse(String token, Map<String, Object> user, String message) {
        this.token = token;
        this.user = user;
        this.message = message;
    }

    //---------------------------------------------------------------------
    // Métodos Getter y Setter
    //---------------------------------------------------------------------
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Map<String, Object> getUser() {
        return user;
    }

    public void setUser(Map<String, Object> user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
