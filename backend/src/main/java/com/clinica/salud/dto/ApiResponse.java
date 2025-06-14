package com.clinica.salud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Wrapper genérico para respuestas API consistentes
 * Estandariza el formato de respuesta en toda la aplicación
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    private String error;
    
    // Constructor para respuestas exitosas
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Operación exitosa", data, LocalDateTime.now(), null);
    }
    
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, message, data, LocalDateTime.now(), null);
    }
    
    // Constructor para respuestas de error
    public static <T> ApiResponse<T> error(String error) {
        return new ApiResponse<>(false, null, null, LocalDateTime.now(), error);
    }
    
    public static <T> ApiResponse<T> error(String error, String message) {
        return new ApiResponse<>(false, message, null, LocalDateTime.now(), error);
    }
    
    // Constructor para respuesta simple de éxito sin datos
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(true, "Operación exitosa", null, LocalDateTime.now(), null);
    }
}
