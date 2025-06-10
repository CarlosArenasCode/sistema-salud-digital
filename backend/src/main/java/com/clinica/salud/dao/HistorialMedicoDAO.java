package com.clinica.salud.dao;

import com.clinica.salud.modelo.HistorialMedico;
import com.clinica.salud.util.Conexion;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HistorialMedicoDAO {    public void insertar(HistorialMedico historial) {
        String sql = "INSERT INTO historiales_medicos (id_paciente, id_medico, id_cita, fecha_visita, diagnostico, tratamiento, medicamentos, observaciones, proxima_cita) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, historial.getPatientId());
            ps.setLong(2, historial.getDoctorId());
            if (historial.getAppointmentId() != null) {
                ps.setLong(3, historial.getAppointmentId());
            } else {
                ps.setNull(3, Types.BIGINT);
            }
            if (historial.getRecordDate() != null) {
                ps.setDate(4, Date.valueOf(historial.getRecordDate()));
            } else {
                ps.setDate(4, Date.valueOf(java.time.LocalDate.now()));
            }
            ps.setString(5, historial.getDiagnosis());
            ps.setString(6, historial.getTreatment());
            ps.setString(7, historial.getPrescription()); // Mapear a medicamentos            ps.setString(8, historial.getNotes()); // Mapear a observaciones
            if (historial.getFollowUpDate() != null) {
                ps.setDate(9, Date.valueOf(historial.getFollowUpDate()));
            } else {
                ps.setNull(9, Types.DATE);
            }
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    historial.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar historial médico: " + e.getMessage(), e);
        }
    }

    public HistorialMedico buscarPorId(int id) {
        String sql = "SELECT * FROM historiales_medicos WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {                if (rs.next()) {
                    HistorialMedico h = new HistorialMedico();
                    h.setId(rs.getInt("id"));
                    h.setPatientId(rs.getInt("id_paciente"));
                    h.setDoctorId(rs.getInt("id_medico"));
                    h.setAppointmentId(rs.getObject("id_cita", Integer.class));
                    h.setDiagnosis(rs.getString("diagnostico"));
                    h.setTreatment(rs.getString("tratamiento"));
                    h.setPrescription(rs.getString("medicamentos")); // Mapear medicamentos a prescription
                    h.setNotes(rs.getString("observaciones")); // Mapear observaciones a notes
                    Date recordDate = rs.getDate("fecha_visita"); // Usar fecha_visita como recordDate
                    if (recordDate != null) {
                        h.setRecordDate(recordDate.toLocalDate());
                    }
                    Date followUpDate = rs.getDate("proxima_cita"); // Usar proxima_cita como followUpDate
                    if (followUpDate != null) {
                        h.setFollowUpDate(followUpDate.toLocalDate());
                    }
                    return h;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar historial médico: " + e.getMessage(), e);
        }
        return null;
    }    public void actualizar(HistorialMedico historial) {
        String sql = "UPDATE historiales_medicos SET id_paciente = ?, id_medico = ?, id_cita = ?, fecha_visita = ?, diagnostico = ?, tratamiento = ?, medicamentos = ?, observaciones = ?, proxima_cita = ? WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, historial.getPatientId());
            ps.setLong(2, historial.getDoctorId());
            if (historial.getAppointmentId() != null) {
                ps.setLong(3, historial.getAppointmentId());
            } else {
                ps.setNull(3, Types.BIGINT);
            }
            if (historial.getRecordDate() != null) {
                ps.setDate(4, Date.valueOf(historial.getRecordDate()));
            } else {
                ps.setDate(4, Date.valueOf(java.time.LocalDate.now()));
            }
            ps.setString(5, historial.getDiagnosis());
            ps.setString(6, historial.getTreatment());
            ps.setString(7, historial.getPrescription()); // Mapear a medicamentos
            ps.setString(8, historial.getNotes()); // Mapear a observaciones
            if (historial.getFollowUpDate() != null) {
                ps.setDate(9, Date.valueOf(historial.getFollowUpDate()));
            } else {
                ps.setNull(9, Types.DATE);
            }
            ps.setInt(10, historial.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar historial médico: " + e.getMessage(), e);
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM historiales_medicos WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar historial médico: " + e.getMessage(), e);
        }
    }    public List<HistorialMedico> listarTodos() {
        List<HistorialMedico> lista = new ArrayList<>();
        String sql = "SELECT * FROM historiales_medicos";
        try (Connection conn = Conexion.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {
                HistorialMedico h = new HistorialMedico();
                h.setId(rs.getInt("id"));
                h.setPatientId(rs.getInt("id_paciente"));
                h.setDoctorId(rs.getInt("id_medico"));
                h.setAppointmentId(rs.getObject("id_cita", Integer.class));
                h.setDiagnosis(rs.getString("diagnostico"));
                h.setTreatment(rs.getString("tratamiento"));
                h.setPrescription(rs.getString("medicamentos")); // Mapear medicamentos a prescription
                h.setNotes(rs.getString("observaciones")); // Mapear observaciones a notes
                Date recordDate = rs.getDate("fecha_visita"); // Usar fecha_visita como recordDate
                if (recordDate != null) {
                    h.setRecordDate(recordDate.toLocalDate());
                }
                Date followUpDate = rs.getDate("proxima_cita"); // Usar proxima_cita como followUpDate
                if (followUpDate != null) {
                    h.setFollowUpDate(followUpDate.toLocalDate());
                }
                lista.add(h);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar historiales médicos: " + e.getMessage(), e);
        }
        return lista;
    }
}
