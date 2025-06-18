package com.clinica.salud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// DTO genérico para respuestas API estandarizadas
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {    
    // Indica si la operación fue exitosa
    private boolean success;
    // Mensaje descriptivo de la respuesta
    private String message;
    // Datos de la respuesta (genérico)
    private T data;
    // Marca de tiempo de la respuesta
    private LocalDateTime timestamp;
    // Mensaje de error si existe
    private String error;    
    // Método estático para crear respuesta exitosa con datos
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Operación exitosa", data, LocalDateTime.now(), null);    }
    
    // Método estático para crear respuesta exitosa con datos y mensaje personalizado
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, message, data, LocalDateTime.now(), null);    }
    
    // Método estático para crear respuesta exitosa simple sin datos
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(true, "Operación exitosa", null, LocalDateTime.now(), null);    }
    
    // Método estático para crear respuesta de error con mensaje
    public static <T> ApiResponse<T> error(String error) {
        return new ApiResponse<>(false, null, null, LocalDateTime.now(), error);    }
    
    // Método estático para crear respuesta de error con mensaje personalizado adicional
    public static <T> ApiResponse<T> error(String error, String message) {
        return new ApiResponse<>(false, message, null, LocalDateTime.now(), error);
    }
}
