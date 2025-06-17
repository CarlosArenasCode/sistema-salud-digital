package com.clinica.salud.controller;

import com.clinica.salud.dto.AuthRequest;
import com.clinica.salud.dto.AuthResponse;
import com.clinica.salud.dto.RegistroRequest;
import com.clinica.salud.service.AuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * ===================================================
 * CONTROLADOR DE AUTENTICACIÓN - SISTEMA DE SALUD DIGITAL
 * ===================================================
 *
 * Este controlador proporciona los endpoints necesarios para la gestión de la autenticación
 * de usuarios en el sistema, incluyendo funcionalidades de registro, inicio de sesión y validación
 * de tokens JWT. Actúa como punto de entrada para todos los procesos relacionados con la seguridad
 * y la identidad de los usuarios.
 *
 * Todos los endpoints están mapeados bajo "/auth" y no requieren autenticación previa.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    
    // ===================================================
    // CONFIGURACIÓN Y DEPENDENCIAS
    // ===================================================
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    // ===================================================
    // AUTENTICACIÓN DE USUARIOS
    // ===================================================
    
    /**
     * Procesa las solicitudes de inicio de sesión validando las credenciales.
     * Si la autenticación es exitosa, genera y devuelve un token JWT.
     * 
     * @param request Credenciales del usuario (nombre y contraseña)
     * @return Token JWT si la autenticación es exitosa
     */    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {
        try {
            AuthResponse authResponse = authService.authenticate(request);
            return ResponseEntity.ok(authResponse);
        } catch (RuntimeException e) {
            logger.error("Error de autenticación para usuario: {}", request.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Credenciales inválidas"));
        } catch (Exception e) {
            logger.error("Error interno durante autenticación: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Error interno del servidor"));
        }
    }
    
    // ===================================================
    // REGISTRO DE NUEVOS USUARIOS
    // ===================================================
    
    /**
     * Registra un nuevo usuario en el sistema de salud digital.
     * 
     * @param request Datos necesarios para el registro del usuario
     * @return Token de autenticación si el registro es exitoso
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegistroRequest request) {
        try {
            AuthResponse authResponse = authService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Error interno del servidor"));
        }
    }

    // ===================================================
    // VALIDACIÓN DE TOKENS
    // ===================================================
    
    /**
     * Valida la autenticidad y vigencia de un token JWT.
     * 
     * @param token El token JWT a validar
     * @return true si el token es válido, false en caso contrario
     */
    @GetMapping("/validate")
    public ResponseEntity<Map<String, Boolean>> validateToken(@RequestParam String token) {
        boolean isValid = authService.validateToken(token);
        return ResponseEntity.ok(Map.of("valid", isValid));
    }
}
