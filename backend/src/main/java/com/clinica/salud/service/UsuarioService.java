package com.clinica.salud.service;

import com.clinica.salud.entity.UsuarioEntity;
import com.clinica.salud.repository.jpa.UsuarioJpaRepository;
import com.clinica.salud.exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

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

    // Método con manejo de excepciones personalizado
    public UsuarioEntity buscarPorId(Long id) {
        return findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario con id " + id + " no encontrado"));
    }

    // Método de actualización con validación
    public UsuarioEntity actualizar(Long id, UsuarioEntity usuario) {
        if (!existsById(id)) {
            throw new RecursoNoEncontradoException("Usuario con id " + id + " no encontrado");
        }
        usuario.setId(id);
        return save(usuario);
    }

    // Método de eliminación con validación
    public void eliminar(Long id) {
        if (!existsById(id)) {
            throw new RecursoNoEncontradoException("Usuario con id " + id + " no encontrado");
        }
        deleteById(id);
    }    // Métodos específicos de Usuario pueden agregarse aquí cuando estén disponibles en el repositorio
    // Por ejemplo: buscarPorUsername, buscarPorRol, etc.
}
