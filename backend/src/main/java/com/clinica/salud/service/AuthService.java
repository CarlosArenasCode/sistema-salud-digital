package com.clinica.salud.service;

import com.clinica.salud.dto.AuthRequest;
import com.clinica.salud.dto.AuthResponse;
import com.clinica.salud.dto.RegistroRequest;
import com.clinica.salud.entity.UsuarioEntity;
import com.clinica.salud.entity.PacienteEntity;
import com.clinica.salud.entity.MedicoEntity;
import com.clinica.salud.repository.jpa.UsuarioJpaRepository;
import com.clinica.salud.repository.jpa.PacienteJpaRepository;
import com.clinica.salud.repository.jpa.MedicoJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Servicio de autenticación ULTRA-SIMPLE
 * Solo validación básica de usuario/contraseña, sin JWT ni seguridad compleja
 */
@Service
public class AuthService {
    
    // ======================================================================
    // ATRIBUTOS - SOLO LO ESENCIAL
    // ======================================================================
    private final UsuarioJpaRepository usuarioRepository;
    private final PacienteJpaRepository pacienteRepository;
    private final MedicoJpaRepository medicoRepository;
    
    // ======================================================================
    // CONSTRUCTOR SIMPLIFICADO
    // ======================================================================
    public AuthService(
            UsuarioJpaRepository usuarioRepository,
            PacienteJpaRepository pacienteRepository,
            MedicoJpaRepository medicoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }    // ======================================================================
    // AUTENTICACIÓN ULTRA-SIMPLE
    // ======================================================================
    /**
     * Login básico: solo validar usuario/contraseña en BD
     */
    public AuthResponse authenticate(AuthRequest authRequest) {
        // 1. Buscar usuario en la base de datos
        UsuarioEntity usuario = usuarioRepository.findByNombreUsuario(authRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Verificar contraseña (comparación directa - sin codificación)
        if (!usuario.getContrasena().equals(authRequest.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // 3. Verificar que el usuario esté activo
        if (!usuario.getActivo()) {
            throw new RuntimeException("Usuario inactivo");
        }

        // 4. Generar token ultra-simple (UUID)
        String token = "simple-" + UUID.randomUUID().toString().substring(0, 8);

        // 5. Preparar datos de respuesta
        Map<String, Object> userData = new HashMap<>();
        userData.put("id", usuario.getId());
        userData.put("username", usuario.getNombreUsuario());
        userData.put("role", usuario.getRol());
        userData.put("email", usuario.getEmail());

        return new AuthResponse(token, userData, "Login exitoso");
    }

    // ======================================================================
    // REGISTRO DE USUARIOS
    // ======================================================================
    /**
     * Registrar un nuevo usuario con registro específico automático
     */
    @Transactional
    public AuthResponse register(RegistroRequest request) {
        // Verificar si el usuario ya existe
        if (usuarioRepository.existsByNombreUsuario(request.getNombreUsuario())) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }
        
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        
        // Crear nuevo usuario
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNombreUsuario(request.getNombreUsuario());
        // Almacenar contraseña en texto plano (sin codificación)
        usuario.setContrasena(request.getPassword());
        usuario.setEmail(request.getEmail());
        usuario.setRol(request.getRol() != null ? request.getRol() : "PACIENTE");
        usuario.setActivo(true);
        
        // Guardar el usuario en la base de datos
        usuario = usuarioRepository.save(usuario);        // Crear registro específico según el rol
        crearRegistroEspecifico(usuario, request);

        // Auto-login después de registro - TOKEN SIMPLE
        String token = "simple-" + UUID.randomUUID().toString().substring(0, 8);

        // Preparar datos de respuesta
        Map<String, Object> userData = new HashMap<>();
        userData.put("id", usuario.getId());
        userData.put("username", usuario.getNombreUsuario());
        userData.put("role", usuario.getRol());
        userData.put("email", usuario.getEmail());

        return new AuthResponse(token, userData, "Usuario registrado exitosamente");
    }

    // ======================================================================
    // MÉTODOS PRIVADOS DE APOYO
    // ======================================================================
    
    /**
     * Crear registro específico según el rol del usuario
     */
    private void crearRegistroEspecifico(UsuarioEntity usuario, RegistroRequest request) {
        switch (usuario.getRol()) {
            case "PACIENTE":
                crearRegistroPaciente(usuario, request);
                break;
            case "MEDICO":
                crearRegistroMedico(usuario, request);
                break;
            case "ADMINISTRADOR":
            case "ENFERMERO":
                // Para estos roles no se requiere registro específico adicional
                break;
            default:
                throw new RuntimeException("Rol no válido: " + usuario.getRol());
        }
    }
    
    /**
     * Crear registro específico de paciente
     */
    private void crearRegistroPaciente(UsuarioEntity usuario, RegistroRequest request) {
        PacienteEntity paciente = PacienteEntity.builder()
                .idUsuario(usuario.getId())
                .nombres(request.getNombres() != null ? request.getNombres() : "Nombre")
                .apellidos(request.getApellidos() != null ? request.getApellidos() : "Apellido")
                .fechaNacimiento(request.getFechaNacimiento() != null ? request.getFechaNacimiento() : LocalDate.of(1990, 1, 1))
                .numeroIdentificacion(request.getNumeroIdentificacion() != null ? 
                    request.getNumeroIdentificacion() : "TEMP" + usuario.getId())
                .email(usuario.getEmail())
                .activo(true)
                .build();
                
        pacienteRepository.save(paciente);
    }
    
    /**
     * Crear registro específico de médico
     */
    private void crearRegistroMedico(UsuarioEntity usuario, RegistroRequest request) {
        MedicoEntity medico = MedicoEntity.builder()
                .idUsuario(usuario.getId())
                .nombres(request.getNombres() != null ? request.getNombres() : "Nombre")
                .apellidos(request.getApellidos() != null ? request.getApellidos() : "Apellido")
                .especializacion(request.getEspecializacion() != null ? request.getEspecializacion() : "Medicina General")
                .numeroLicencia("LIC" + usuario.getId())
                .email(usuario.getEmail())
                .activo(true)
                .build();
                
        medicoRepository.save(medico);
    }    // ======================================================================
    // VALIDACIÓN SIMPLE DE TOKENS
    // ======================================================================
    /**
     * Validación básica de token (solo verifica que no esté vacío)
     */
    public boolean validateToken(String token) {
        // Validación ultra-simple: solo verificar que no esté vacío y tenga formato básico
        return token != null && !token.trim().isEmpty() && token.startsWith("simple-");
    }
}
