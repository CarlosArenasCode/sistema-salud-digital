package com.clinica.salud.dao;

import com.clinica.salud.modelo.Paciente;
import com.clinica.salud.util.Conexion;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PacienteDAO {    public void insertar(Paciente paciente) {        String sql = "INSERT INTO pacientes (primer_nombre, apellido, fecha_nacimiento, direccion, telefono, correo_electronico, genero) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // Separamos el nombre completo en nombre y apellido
            String[] nombreCompleto = paciente.getName().split(" ", 2);
            String firstName = nombreCompleto[0];
            String lastName = nombreCompleto.length > 1 ? nombreCompleto[1] : "";
            
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setDate(3, java.sql.Date.valueOf(paciente.getDateOfBirth()));
            ps.setString(4, paciente.getAddress());
            ps.setString(5, paciente.getPhone());
            ps.setString(6, paciente.getEmail());            // Convertimos el genero al formato esperado por el esquema (M -> MASCULINO, F -> FEMENINO)
            String gender = "OTRO";
            if ("M".equalsIgnoreCase(paciente.getGender())) {
                gender = "MASCULINO";
            } else if ("F".equalsIgnoreCase(paciente.getGender())) {
                gender = "FEMENINO";
            }
            ps.setString(7, gender);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar paciente", e);
        }
    }    public List<Paciente> listarTodos() {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT * FROM pacientes";
        try (Connection conn = Conexion.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {                // Combinamos nombre y apellido
                String nombreCompleto = rs.getString("primer_nombre") + " " + rs.getString("apellido");
                
                // Convertimos el género al formato esperado por el modelo
                String genero = "O";
                String dbGenero = rs.getString("genero");
                if ("MASCULINO".equalsIgnoreCase(dbGenero)) {
                    genero = "M";
                } else if ("FEMENINO".equalsIgnoreCase(dbGenero)) {
                    genero = "F";
                }
                
                Paciente p = new Paciente(
                        rs.getInt("id"),
                        nombreCompleto,
                        rs.getDate("fecha_nacimiento").toString(),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("correo_electronico"),
                        genero
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar pacientes", e);
        }
        return lista;
    }    public Paciente buscarPorId(int id) {
        String sql = "SELECT * FROM pacientes WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {                    // Combinamos nombre y apellido
                    String nombreCompleto = rs.getString("primer_nombre") + " " + rs.getString("apellido");
                    
                    // Convertimos el género al formato esperado por el modelo
                    String genero = "O";
                    String dbGenero = rs.getString("genero");
                    if ("MASCULINO".equalsIgnoreCase(dbGenero)) {
                        genero = "M";
                    } else if ("FEMENINO".equalsIgnoreCase(dbGenero)) {
                        genero = "F";
                    }
                    
                    return new Paciente(
                        rs.getInt("id"),
                        nombreCompleto,
                        rs.getDate("fecha_nacimiento").toString(),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("correo_electronico"),
                        genero
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar paciente", e);
        }
        return null;
    }    public void actualizar(Paciente paciente) {
        String sql = "UPDATE pacientes SET primer_nombre = ?, apellido = ?, fecha_nacimiento = ?, direccion = ?, telefono = ?, correo_electronico = ?, genero = ? WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // Separamos el nombre completo en nombre y apellido
            String[] nombreCompleto = paciente.getName().split(" ", 2);
            String firstName = nombreCompleto[0];
            String lastName = nombreCompleto.length > 1 ? nombreCompleto[1] : "";
            
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setDate(3, java.sql.Date.valueOf(paciente.getDateOfBirth()));
            ps.setString(4, paciente.getAddress());
            ps.setString(5, paciente.getPhone());
            ps.setString(6, paciente.getEmail());
              // Convertimos el genero al formato esperado por el esquema
            String gender = "OTRO";
            if ("M".equalsIgnoreCase(paciente.getGender())) {
                gender = "MASCULINO";
            } else if ("F".equalsIgnoreCase(paciente.getGender())) {
                gender = "FEMENINO";
            }
            ps.setString(7, gender);
            ps.setInt(8, paciente.getPatientId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar paciente", e);
        }
    }    public void eliminar(int id) {
        String sql = "DELETE FROM pacientes WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar paciente", e);
        }
    }
}
