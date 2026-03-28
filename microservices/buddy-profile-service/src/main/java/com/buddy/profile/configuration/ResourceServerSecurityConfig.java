package com.buddy.profile.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ResourceServerSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Define public endpoints for this service here if needed
                .anyRequest().authenticated() // All other requests require a valid JWT
            )
            // This single line enables JWT validation using the jwk-set-uri from your yaml
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> {}) 
            );

        return http.build();
    }
}