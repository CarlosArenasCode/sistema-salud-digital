package com.clinica.salud.dao;

import com.clinica.salud.modelo.Cita;
import com.clinica.salud.util.Conexion;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CitaDAO {public void insertar(Cita cita) {
        String sql = "INSERT INTO citas (id_paciente, id_medico, fecha_cita, hora_cita, motivo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cita.getIdPaciente());
            ps.setInt(2, cita.getIdMedico());
            ps.setDate(3, java.sql.Date.valueOf(cita.getFecha())); // Assuming fecha is in YYYY-MM-DD format
            ps.setTime(4, java.sql.Time.valueOf(cita.getHora())); // Assuming hora is in HH:MM:SS format
            ps.setString(5, cita.getMotivo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar cita", e);
        }
    }    public List<Cita> listarTodos() {
        List<Cita> lista = new ArrayList<>();
        String sql = "SELECT * FROM citas";
        try (Connection conn = Conexion.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {                Cita c = new Cita(
                        rs.getInt("id_cita"),
                        rs.getInt("id_paciente"),
                        rs.getInt("id_medico"),
                        rs.getDate("fecha_cita").toString(),
                        rs.getTime("hora_cita").toString(),
                        rs.getString("motivo")
                );
                lista.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar citas", e);
        }
        return lista;
    }

    public Cita buscarPorId(int id) {
        String sql = "SELECT * FROM citas WHERE id_cita = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {                    return new Cita(
                        rs.getInt("id_cita"),
                        rs.getInt("id_paciente"),
                        rs.getInt("id_medico"),
                        rs.getDate("fecha_cita").toString(),
                        rs.getTime("hora_cita").toString(),
                        rs.getString("motivo")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar cita por ID", e);
        }
        return null;
    }

    public void actualizar(Cita cita) {
        String sql = "UPDATE citas SET id_paciente = ?, id_medico = ?, fecha_cita = ?, hora_cita = ?, motivo = ? WHERE id_cita = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cita.getIdPaciente());
            ps.setInt(2, cita.getIdMedico());
            ps.setDate(3, java.sql.Date.valueOf(cita.getFecha()));
            ps.setTime(4, java.sql.Time.valueOf(cita.getHora()));
            ps.setString(5, cita.getMotivo());
            ps.setInt(6, cita.getIdCita());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar cita", e);
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM citas WHERE id_cita = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar cita", e);
        }
    }
}
