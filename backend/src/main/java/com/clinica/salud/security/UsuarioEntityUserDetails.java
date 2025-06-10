package com.clinica.salud.security;

import com.clinica.salud.entity.UsuarioEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Wrapper de UserDetails para UsuarioEntity
 */
public class UsuarioEntityUserDetails implements UserDetails {
    
    private final UsuarioEntity usuario;
    
    public UsuarioEntityUserDetails(UsuarioEntity usuario) {
        this.usuario = usuario;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + usuario.getRol()));
    }
    
    @Override
    public String getPassword() {
        return usuario.getContrasena();
    }
    
    @Override
    public String getUsername() {
        return usuario.getNombreUsuario();
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return usuario.getActivo();
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return usuario.getActivo();
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return usuario.getActivo();
    }
    
    @Override
    public boolean isEnabled() {
        return usuario.getActivo();
    }
    
    public UsuarioEntity getUsuario() {
        return usuario;
    }
}
