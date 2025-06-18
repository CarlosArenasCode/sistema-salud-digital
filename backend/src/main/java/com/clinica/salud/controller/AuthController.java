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

// Controlador REST para autenticación de usuarios con JWT
@RestController
@RequestMapping("/auth")
public class AuthController {    
    // Logger para registro de eventos de autenticación
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    // Servicio de autenticación inyectado por Spring
    @Autowired
    private AuthService authService;    // Endpoint POST /auth/login Autentica usuario y retorna token JWT
    @PostMapping("/login")
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
    
    // Endpoint POST /auth/register - Registra nuevo usuario en el sistema
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
        
    }    // Endpoint GET /auth/validate - Valida token JWT recibido como parámetro
    @GetMapping("/validate")
    public ResponseEntity<Map<String, Boolean>> validateToken(@RequestParam String token) {
        boolean isValid = authService.validateToken(token);
        return ResponseEntity.ok(Map.of("valid", isValid));
    }
}
