package com.clinica.salud.dao;

import com.clinica.salud.modelo.Usuario;
import com.clinica.salud.util.Conexion;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioDAO {    public void insertar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre_usuario, correo_electronico, contrasena, rol) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getPassword());
            ps.setString(4, usuario.getRole());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar usuario", e);
        }
    }    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Connection conn = Conexion.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id"));
                u.setNombreUsuario(rs.getString("nombre_usuario"));
                u.setEmail(rs.getString("correo_electronico"));
                u.setPassword(rs.getString("contrasena"));
                u.setRole(rs.getString("rol"));
                u.setCreatedAt(rs.getTimestamp("fecha_creacion") != null ? 
                             rs.getTimestamp("fecha_creacion").toLocalDateTime() : null);
                u.setUpdatedAt(rs.getTimestamp("fecha_actualizacion") != null ? 
                             rs.getTimestamp("fecha_actualizacion").toLocalDateTime() : null);
                lista.add(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar usuarios", e);
        }
        return lista;
    }    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("id"));
                    u.setNombreUsuario(rs.getString("nombre_usuario"));
                    u.setEmail(rs.getString("correo_electronico"));
                    u.setPassword(rs.getString("contrasena"));
                    u.setRole(rs.getString("rol"));
                    u.setCreatedAt(rs.getTimestamp("fecha_creacion") != null ? 
                                 rs.getTimestamp("fecha_creacion").toLocalDateTime() : null);
                    u.setUpdatedAt(rs.getTimestamp("fecha_actualizacion") != null ? 
                                 rs.getTimestamp("fecha_actualizacion").toLocalDateTime() : null);
                    return u;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuario por ID", e);
        }
        return null;
    }    public void actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre_usuario = ?, correo_electronico = ?, contrasena = ?, rol = ?, fecha_actualizacion = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getPassword());
            ps.setString(4, usuario.getRole());
            ps.setInt(5, usuario.getIdUsuario());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar usuario", e);
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar usuario", e);
        }
    }
}
