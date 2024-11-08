package org.example.proyectoticg4.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allow CORS for all paths
                .allowedOrigins("http://localhost:5173") // Allow CORS for this origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow specific HTTP methods
                .allowedHeaders("*") // Allow all headers
                .allowCredentials(true) // Allow credentials such as cookies or Authorization headers
                .maxAge(3600); // Set max age for preflight requests
    }
}
