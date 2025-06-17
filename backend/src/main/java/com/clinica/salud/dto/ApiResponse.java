package com.clinica.salud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Wrapper genérico para respuestas API consistentes
 * Estandariza el formato de respuesta en toda la aplicación
 */

//------------- CONFIGURACIÓN DE LA CLASE -------------//
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    
    //------------- ATRIBUTOS -------------//
    private boolean success;     // Indica si la operación fue exitosa
    private String message;      // Mensaje descriptivo de la respuesta
    private T data;              // Datos de la respuesta (genérico)
    private LocalDateTime timestamp; // Marca de tiempo de la respuesta
    private String error;        // Mensaje de error si existe
    
    //------------- CONSTRUCTORES PARA RESPUESTAS EXITOSAS -------------//
    /**
     * Constructor para respuestas exitosas con datos
     * @param data Los datos a devolver en la respuesta
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Operación exitosa", data, LocalDateTime.now(), null);
    }
    
    /**
     * Constructor para respuestas exitosas con datos y mensaje personalizado
     * @param data Los datos a devolver
     * @param message Mensaje personalizado
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, message, data, LocalDateTime.now(), null);
    }
    
    /**
     * Constructor para respuesta simple de éxito sin datos
     */
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(true, "Operación exitosa", null, LocalDateTime.now(), null);
    }
    
    //------------- CONSTRUCTORES PARA RESPUESTAS DE ERROR -------------//
    /**
     * Constructor para respuestas de error con mensaje de error
     * @param error Mensaje de error
     */
    public static <T> ApiResponse<T> error(String error) {
        return new ApiResponse<>(false, null, null, LocalDateTime.now(), error);
    }
    
    /**
     * Constructor para respuestas de error con mensaje personalizado
     * @param error Mensaje de error
     * @param message Mensaje adicional
     */
    public static <T> ApiResponse<T> error(String error, String message) {
        return new ApiResponse<>(false, message, null, LocalDateTime.now(), error);
    }
}
