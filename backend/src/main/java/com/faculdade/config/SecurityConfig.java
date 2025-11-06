package com.faculdade.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfig(CorsConfigurationSource corsConfigurationSource) {
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Desabilita CSRF para APIs REST (stateless)
            .csrf(AbstractHttpConfigurer::disable)
            
            // Configura CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource))
            
            // Configura a política de sessão como STATELESS (API REST)
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            // Configura as autorizações de acesso
            .authorizeHttpRequests(auth -> auth
                // Rotas públicas (não precisam de autenticação)
                .requestMatchers(
                    "/api/auth/**",           // Endpoints de autenticação
                    "/swagger-ui/**",         // Swagger UI
                    "/v3/api-docs/**",       // OpenAPI docs
                    "/swagger-ui.html",       // Swagger UI (alternativo)
                    "/actuator/**"           // Spring Actuator
                ).permitAll()
                
                // Todas as outras rotas requerem autenticação
                .anyRequest().authenticated()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

