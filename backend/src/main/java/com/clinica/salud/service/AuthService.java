package com.clinica.salud.service;

import com.clinica.salud.dto.AuthRequest;
import com.clinica.salud.dto.AuthResponse;
import com.clinica.salud.dto.RegistroRequest;
import com.clinica.salud.entity.UsuarioEntity;
import com.clinica.salud.entity.RolUsuario;
import com.clinica.salud.exception.RecursoNoEncontradoException;
import com.clinica.salud.repository.jpa.UsuarioJpaRepository;
import com.clinica.salud.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Servicio para manejar la autenticación y registro de usuarios
 */
@Service
public class AuthService {    private final AuthenticationManager authenticationManager;
    private final UsuarioJpaRepository usuarioRepository;
    private final JwtTokenProvider tokenProvider;    @Autowired
    public AuthService(
            AuthenticationManager authenticationManager,
            UsuarioJpaRepository usuarioRepository,
            JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.tokenProvider = tokenProvider;
    }

    /**
     * Autenticar un usuario y generar token JWT
     */    public AuthResponse authenticate(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        
        String token = tokenProvider.generateToken(authentication);

        UsuarioEntity usuario = usuarioRepository.findByNombreUsuario(authRequest.getUsername())
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        Map<String, Object> userData = new HashMap<>();
        userData.put("id", usuario.getId());
        userData.put("username", usuario.getNombreUsuario());
        userData.put("role", usuario.getRol());

        return new AuthResponse(token, userData);
    }

    /**
     * Registrar un nuevo usuario
     */
    public AuthResponse register(RegistroRequest request) {
        // Verificar si el usuario ya existe
        if (usuarioRepository.existsByNombreUsuario(request.getNombreUsuario())) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }
        
        // Crear nuevo usuario
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNombreUsuario(request.getNombreUsuario());
        usuario.setContrasena(request.getPassword());
        usuario.setEmail(request.getEmail());
        usuario.setRol("PACIENTE");
        
        usuarioRepository.save(usuario);

        // Autenticar automáticamente al usuario registrado
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getNombreUsuario(), request.getPassword())
        );

        String token = tokenProvider.generateToken(authentication);

        Map<String, Object> userData = new HashMap<>();
        userData.put("id", usuario.getId());
        userData.put("username", usuario.getNombreUsuario());
        userData.put("role", usuario.getRol());

        return new AuthResponse(token, userData, "Usuario registrado exitosamente");
    }

    /**
     * Validar un token JWT
     */
    public boolean validateToken(String token) {
        return tokenProvider.validateToken(token);
    }
}

