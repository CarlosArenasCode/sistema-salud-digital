// ---------------------------------------
// Declaración del paquete de la aplicación
// ---------------------------------------
package com.clinica.salud;

// ---------------------------------------
// Importaciones necesarias
// ---------------------------------------
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// ---------------------------------------
// Clase principal de la aplicación
// ---------------------------------------
@SpringBootApplication
public class BackendApplication {
    
    // ---------------------------------------
    // Método principal que inicia la aplicación Spring Boot
    // ---------------------------------------
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}
