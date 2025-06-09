package com.clinica.salud.controller;

import com.clinica.salud.dto.AuthRequest;
import com.clinica.salud.dto.AuthResponse;
import com.clinica.salud.dto.RegistroRequest;
import com.clinica.salud.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Endpoints para inicio de sesión y registro")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;@PostMapping("/login")
    @Operation(summary = "Inicia sesión de usuario y devuelve token JWT")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {
        try {
            AuthResponse authResponse = authService.authenticate(request);
            return ResponseEntity.ok(authResponse);        } catch (BadCredentialsException e) {
            logger.error("Credenciales inválidas para usuario: {}", request.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Credenciales inválidas"));
        } catch (Exception e) {
            logger.error("Error interno durante autenticación: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Error interno del servidor"));
        }
    }    @PostMapping("/register")
    @Operation(summary = "Registra un nuevo usuario")
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
    }    @GetMapping("/validate")
    @Operation(summary = "Valida un token JWT")
    public ResponseEntity<Map<String, Boolean>> validateToken(@RequestParam String token) {
        boolean isValid = authService.validateToken(token);
        return ResponseEntity.ok(Map.of("valid", isValid));
    }
}
