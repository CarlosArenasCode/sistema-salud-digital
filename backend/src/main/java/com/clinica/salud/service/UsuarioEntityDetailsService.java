package com.clinica.salud.service;

import com.clinica.salud.entity.UsuarioEntity;
import com.clinica.salud.repository.jpa.UsuarioJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Servicio para cargar los detalles del usuario para autenticación
 */
@Service
public class UsuarioEntityDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioJpaRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioEntity usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        
        if (!usuario.getActivo()) {
            throw new UsernameNotFoundException("Usuario desactivado: " + username);
        }
        
        return usuario;
    }
}
