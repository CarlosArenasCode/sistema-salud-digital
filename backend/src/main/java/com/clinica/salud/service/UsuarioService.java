package com.clinica.salud.service;

import com.clinica.salud.dao.UsuarioDAO;
import com.clinica.salud.exception.RecursoNoEncontradoException;
import com.clinica.salud.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public List<Usuario> listarTodos() {
        return usuarioDAO.listarTodos();
    }

    public Usuario buscarPorId(int id) {
        Usuario usuario = usuarioDAO.buscarPorId(id);
        if (usuario == null) {
            throw new RecursoNoEncontradoException("Usuario con id " + id + " no encontrado");
        }
        return usuario;
    }

    public void insertar(Usuario usuario) {
        usuarioDAO.insertar(usuario);
    }

    public void actualizar(int id, Usuario usuario) {
        if (usuarioDAO.buscarPorId(id) == null) {
            throw new RecursoNoEncontradoException("Usuario con id " + id + " no encontrado");
        }
        usuario.setIdUsuario(id);
        usuarioDAO.actualizar(usuario);
    }

    public void eliminar(int id) {
        if (usuarioDAO.buscarPorId(id) == null) {
            throw new RecursoNoEncontradoException("Usuario con id " + id + " no encontrado");
        }
        usuarioDAO.eliminar(id);
    }

    public void registrarUsuario(Usuario usuario) {
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        usuarioDAO.insertar(usuario);
    }
}
