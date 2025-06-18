package com.clinica.salud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

// --- Definición de Clase de Configuración ---
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // --- Configuración de Recursos Estáticos ---
    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    // --- Resolución Personalizada de Recursos ---
                    @Override
                    protected Resource getResource(@NonNull String resourcePath, @NonNull Resource location) throws IOException {
                        Resource requestedResource = location.createRelative(resourcePath);
                        
                        // Si el archivo existe, lo devolvemos
                        if (requestedResource.exists() && requestedResource.isReadable()) {
                            return requestedResource;
                        }
                        
                        // Si no existe y no es una ruta de API, devolver login.html como página por defecto
                        if (!resourcePath.startsWith("api/")) {
                            return new ClassPathResource("/static/login.html");
                        }
                        
                        return null;
                    }
                });
    }
}
