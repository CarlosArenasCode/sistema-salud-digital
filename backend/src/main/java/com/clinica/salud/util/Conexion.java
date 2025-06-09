package com.clinica.salud.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {
    private static HikariDataSource dataSource;

    static {
        try (InputStream input = Conexion.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                throw new RuntimeException("No se encontró el archivo db.properties");
            }
            prop.load(input);

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(prop.getProperty("db.url"));
            config.setUsername(prop.getProperty("db.user"));
            config.setPassword(prop.getProperty("db.password"));
            config.setMaximumPoolSize(Integer.parseInt(prop.getProperty("db.poolSize", "10")));

            dataSource = new HikariDataSource(config);
        } catch (IOException e) {
            throw new RuntimeException("Error cargando db.properties", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
