package com.clinica.salud.dao;

import com.clinica.salud.modelo.Factura;
import com.clinica.salud.util.Conexion;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FacturaDAO {public void insertar(Factura factura) {
        String sql = "INSERT INTO facturas (id_paciente, fecha, monto) VALUES (?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, factura.getIdPaciente());
            ps.setString(2, factura.getFecha());
            ps.setDouble(3, factura.getMonto());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar factura", e);
        }
    }    public List<Factura> listarTodos() {
        List<Factura> lista = new ArrayList<>();
        String sql = "SELECT * FROM facturas";
        try (Connection conn = Conexion.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {
                Factura f = new Factura(
                        rs.getInt("id_factura"),
                        rs.getInt("id_paciente"),
                        rs.getString("fecha"),
                        rs.getDouble("monto")
                );
                lista.add(f);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar facturas", e);
        }
        return lista;
    }    public Factura buscarPorId(int id) {
        String sql = "SELECT * FROM facturas WHERE id_factura = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Factura(
                        rs.getInt("id_factura"),
                        rs.getInt("id_paciente"),
                        rs.getString("fecha"),
                        rs.getDouble("monto")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar factura por ID", e);
        }
        return null;
    }    public void actualizar(Factura factura) {
        String sql = "UPDATE facturas SET id_paciente = ?, fecha = ?, monto = ? WHERE id_factura = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, factura.getIdPaciente());
            ps.setString(2, factura.getFecha());
            ps.setDouble(3, factura.getMonto());
            ps.setInt(4, factura.getIdFactura());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar factura", e);
        }
    }    public void eliminar(int id) {
        String sql = "DELETE FROM facturas WHERE id_factura = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar factura", e);
        }
    }
}
