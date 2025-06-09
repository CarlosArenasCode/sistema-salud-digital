package com.clinica.salud.dao;

import com.clinica.salud.modelo.Medicamento;
import com.clinica.salud.util.Conexion;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicamentoDAO {

    public void insertar(Medicamento medicamento) {
        String sql = "INSERT INTO medicamentos (nombre, descripcion, fabricante, concentracion, forma) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, medicamento.getNombre());
            ps.setString(2, medicamento.getDescripcion());
            ps.setString(3, medicamento.getFabricante());
            ps.setString(4, medicamento.getConcentracion());
            ps.setString(5, medicamento.getForma());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    medicamento.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar medicamento: " + e.getMessage(), e);
        }
    }

    public List<Medicamento> listarTodos() {
        List<Medicamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM medicamentos";
        try (Connection conn = Conexion.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {
                Medicamento m = new Medicamento();
                m.setId(rs.getLong("id"));
                m.setNombre(rs.getString("nombre"));
                m.setDescripcion(rs.getString("descripcion"));
                m.setFabricante(rs.getString("fabricante"));
                m.setConcentracion(rs.getString("concentracion"));
                m.setForma(rs.getString("forma"));
                lista.add(m);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar medicamentos: " + e.getMessage(), e);
        }
        return lista;
    }

    public Medicamento buscarPorId(int id) {
        String sql = "SELECT * FROM medicamentos WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Medicamento m = new Medicamento();
                    m.setId(rs.getLong("id"));
                    m.setNombre(rs.getString("nombre"));
                    m.setDescripcion(rs.getString("descripcion"));
                    m.setFabricante(rs.getString("fabricante"));
                    m.setConcentracion(rs.getString("concentracion"));
                    m.setForma(rs.getString("forma"));
                    return m;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar medicamento por ID: " + id + ", " + e.getMessage(), e);
        }        return null;
    }

    public void actualizar(Medicamento medicamento) {
        String sql = "UPDATE medicamentos SET nombre = ?, descripcion = ?, fabricante = ?, concentracion = ?, forma = ? WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, medicamento.getNombre());
            ps.setString(2, medicamento.getDescripcion());
            ps.setString(3, medicamento.getFabricante());
            ps.setString(4, medicamento.getConcentracion());
            ps.setString(5, medicamento.getForma());
            ps.setLong(6, medicamento.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar medicamento: " + e.getMessage(), e);
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM medicamentos WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar medicamento: " + e.getMessage(), e);
        }
    }
}
