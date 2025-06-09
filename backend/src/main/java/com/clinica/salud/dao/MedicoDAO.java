package com.clinica.salud.dao;

import com.clinica.salud.modelo.Medico;
import com.clinica.salud.util.Conexion;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicoDAO {
    public void insertar(Medico medico) {
        String sql = "INSERT INTO medicos (id_usuario, primer_nombre, apellido, especializacion, numero_licencia, telefono, correo_electronico, anos_experiencia, tarifa_consulta, dias_disponibles, horas_disponibles) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setObject(1, medico.getUserId()); // Use setObject for nullable Integer
            ps.setString(2, medico.getFirstName());
            ps.setString(3, medico.getLastName());
            ps.setString(4, medico.getSpecialization());
            ps.setString(5, medico.getLicenseNumber());
            ps.setString(6, medico.getPhone());
            ps.setString(7, medico.getEmail());
            ps.setObject(8, medico.getYearsOfExperience()); // Use setObject for nullable Integer
            ps.setBigDecimal(9, medico.getConsultationFee());
            ps.setString(10, medico.getAvailableDays());
            ps.setString(11, medico.getAvailableHours());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    medico.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar médico: " + e.getMessage(), e);
        }
    }

    public List<Medico> listarTodos() {
        List<Medico> lista = new ArrayList<>();
        String sql = "SELECT * FROM medicos";
        try (Connection conn = Conexion.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {                Medico m = new Medico();
                m.setId(rs.getInt("id"));
                m.setUserId(rs.getObject("id_usuario", Integer.class));
                m.setFirstName(rs.getString("primer_nombre"));
                m.setLastName(rs.getString("apellido"));
                m.setSpecialization(rs.getString("especializacion"));
                m.setLicenseNumber(rs.getString("numero_licencia"));
                m.setPhone(rs.getString("telefono"));
                m.setEmail(rs.getString("correo_electronico"));
                m.setYearsOfExperience(rs.getObject("anos_experiencia", Integer.class));
                m.setConsultationFee(rs.getBigDecimal("tarifa_consulta"));
                m.setAvailableDays(rs.getString("dias_disponibles"));
                m.setAvailableHours(rs.getString("horas_disponibles"));
                lista.add(m);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar médicos: " + e.getMessage(), e);
        }
        return lista;
    }

    public Medico buscarPorId(int id) {
        String sql = "SELECT * FROM medicos WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Medico m = new Medico();                    m.setId(rs.getInt("id"));
                    m.setUserId(rs.getObject("id_usuario", Integer.class));
                    m.setFirstName(rs.getString("primer_nombre"));
                    m.setLastName(rs.getString("apellido"));
                    m.setSpecialization(rs.getString("especializacion"));
                    m.setLicenseNumber(rs.getString("numero_licencia"));
                    m.setPhone(rs.getString("telefono"));
                    m.setEmail(rs.getString("correo_electronico"));
                    m.setYearsOfExperience(rs.getObject("anos_experiencia", Integer.class));
                    m.setConsultationFee(rs.getBigDecimal("tarifa_consulta"));
                    m.setAvailableDays(rs.getString("dias_disponibles"));
                    m.setAvailableHours(rs.getString("horas_disponibles"));
                    return m;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar médico por ID: " + e.getMessage(), e);
        }
        return null;
    }

    public void actualizar(Medico medico) {
        String sql = "UPDATE medicos SET id_usuario = ?, primer_nombre = ?, apellido = ?, especializacion = ?, numero_licencia = ?, telefono = ?, correo_electronico = ?, anos_experiencia = ?, tarifa_consulta = ?, dias_disponibles = ?, horas_disponibles = ? WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, medico.getUserId());
            ps.setString(2, medico.getFirstName());
            ps.setString(3, medico.getLastName());
            ps.setString(4, medico.getSpecialization());
            ps.setString(5, medico.getLicenseNumber());
            ps.setString(6, medico.getPhone());
            ps.setString(7, medico.getEmail());
            ps.setObject(8, medico.getYearsOfExperience());
            ps.setBigDecimal(9, medico.getConsultationFee());
            ps.setString(10, medico.getAvailableDays());
            ps.setString(11, medico.getAvailableHours());
            ps.setInt(12, medico.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar médico: " + e.getMessage(), e);
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM medicos WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar médico: " + e.getMessage(), e);
        }
    }
}
