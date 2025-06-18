package com.clinica.salud.config;

// Importaciones necesarias
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Clase de configuración de API
@Configuration
public class ApiConfig implements WebMvcConfigurer {

    // Configuración de prefijo para rutas REST
    @Override
    public void configurePathMatch(@NonNull PathMatchConfigurer configurer) {
        // Agregar prefijo /api a todos los controladores REST
        configurer.addPathPrefix("/api", c -> c.isAnnotationPresent(RestController.class));
    }
}
