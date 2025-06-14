package com.clinica.salud.service;

import com.clinica.salud.entity.UsuarioEntity;
import com.clinica.salud.repository.jpa.UsuarioJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de Usuario
 * Usa BaseService para eliminar código duplicado
 * Incluye métodos específicos de usuarios
 */
@Service
public class UsuarioService extends BaseService<UsuarioEntity, Long> {

    private final UsuarioJpaRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioJpaRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected JpaRepository<UsuarioEntity, Long> getRepository() {
        return usuarioRepository;
    }

    @Override
    protected String getEntityName() {
        return "Usuario";
    }

    // Métodos específicos de Usuario
    public Optional<UsuarioEntity> buscarPorNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }
    
    public Optional<UsuarioEntity> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    
    public List<UsuarioEntity> buscarPorRol(String rol) {
        return usuarioRepository.findByRol(rol);
    }
    
    public List<UsuarioEntity> buscarActivos() {
        return usuarioRepository.findByActivo(true);
    }
    
    public boolean existePorNombreUsuario(String nombreUsuario) {
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }
    
    public boolean existePorEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}
