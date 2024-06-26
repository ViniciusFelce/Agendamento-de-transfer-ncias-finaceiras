package com.project.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*"); // Permite requisições de todos os domínios.
        config.addAllowedMethod("*"); // Permite todos os métodos HTTP (GET, POST, PUT, DELETE).
        config.addAllowedHeader("*"); // Permite todos os headers HTTP.
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
