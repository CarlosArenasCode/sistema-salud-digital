package com.clinica.salud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.clinica.salud.security.JwtTokenProvider;

@Configuration
public class JwtConfig {
    
    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider();
    }
}