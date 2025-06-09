package com.clinica.salud.dao;

import com.clinica.salud.modelo.ResultadoLaboratorio;
import com.clinica.salud.util.Conexion;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ResultadoLaboratorioDAO {public void insertar(ResultadoLaboratorio resultado) {
        String sql = "INSERT INTO resultados_laboratorio (id_paciente, nombre_examen, resultados, fecha_examen) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, resultado.getIdPaciente());
            ps.setString(2, resultado.getTipoExamen());
            ps.setString(3, resultado.getResultado());
            ps.setString(4, resultado.getFecha());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar resultado de laboratorio", e);
        }
    }    public List<ResultadoLaboratorio> listarTodos() {
        List<ResultadoLaboratorio> lista = new ArrayList<>();
        String sql = "SELECT * FROM resultados_laboratorio";
        try (Connection conn = Conexion.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {
                ResultadoLaboratorio r = new ResultadoLaboratorio(
                        rs.getInt("id"),
                        rs.getInt("id_paciente"),
                        rs.getString("nombre_examen"),
                        rs.getString("resultados"),
                        rs.getString("fecha_examen")
                );
                lista.add(r);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar resultados de laboratorio", e);
        }
        return lista;
    }    public ResultadoLaboratorio buscarPorId(int id) {
        String sql = "SELECT * FROM resultados_laboratorio WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ResultadoLaboratorio(
                        rs.getInt("id"),
                        rs.getInt("id_paciente"),
                        rs.getString("nombre_examen"),
                        rs.getString("resultados"),
                        rs.getString("fecha_examen")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar resultado de laboratorio por ID", e);
        }
        return null;
    }    public void actualizar(ResultadoLaboratorio resultado) {
        String sql = "UPDATE resultados_laboratorio SET id_paciente = ?, nombre_examen = ?, resultados = ?, fecha_examen = ? WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, resultado.getIdPaciente());
            ps.setString(2, resultado.getTipoExamen());
            ps.setString(3, resultado.getResultado());
            ps.setString(4, resultado.getFecha());
            ps.setInt(5, resultado.getIdResultado());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar resultado de laboratorio", e);
        }
    }    public void eliminar(int id) {
        String sql = "DELETE FROM resultados_laboratorio WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar resultado de laboratorio", e);
        }
    }
}
