package com.clinica.salud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthController {

    @Autowired
    private DataSource dataSource;

    @GetMapping
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now());
        response.put("application", "Sistema de Salud Digital");
        response.put("version", "1.0.0");
        
        // Detectar automáticamente la base de datos
        String databaseInfo = "Unknown Database";
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            String databaseName = metaData.getDatabaseProductName();
            String databaseVersion = metaData.getDatabaseProductVersion();
            databaseInfo = databaseName + " " + databaseVersion + " Connected";
        } catch (Exception e) {
            databaseInfo = "Database Connection Error: " + e.getMessage();
        }
        
        response.put("database", databaseInfo);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> info() {
        Map<String, Object> response = new HashMap<>();
        response.put("application", "Sistema de Salud Digital Backend");
        response.put("description", "API REST para gestión de sistema de salud");
        response.put("version", "1.0.0");
        response.put("author", "Health System Team");
        response.put("endpoints", new String[]{
            "/api/health - Estado del sistema",
            "/api/usuarios - Gestión de usuarios", 
            "/api/pacientes - Gestión de pacientes",
            "/api/medicos - Gestión de médicos",
            "/api/citas - Gestión de citas",
            "/api/historiales - Gestión de historiales médicos"
        });
        return ResponseEntity.ok(response);
    }
}
