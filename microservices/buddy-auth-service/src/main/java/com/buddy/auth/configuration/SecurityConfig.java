package com.buddy.auth.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.buddy.auth.service.UserDetailsServiceAdapter;

import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

//	private final RsaKeyProperties rsaKeyProperties;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsServiceAdapter userDetailsServiceAdapter;
    

    /**
     * BCrypt is the recommended password encoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * DaoAuthenticationProvider uses our UserDetailsService and PasswordEncoder.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsServiceAdapter);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Provides AuthenticationManager from AuthenticationConfiguration.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Core Spring Security configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF (because we’re using JWT)
            .csrf(csrf -> csrf.disable())

            // Allow unauthenticated access to specific endpoints
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                	"/internal/**",
                    "/auth/register",
                    "/auth/login",
                    "/auth/refresh-token",
                    "/auth/password/**",
                    "/.well-known/jwks.json",
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/error"
                ).permitAll()
                .anyRequest().authenticated()
            )

            // Stateless session management (JWT only)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // Set our AuthenticationProvider
            .authenticationProvider(authenticationProvider())

            // Add JWT validation filter
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
//    @Bean
//    public JwtEncoder jwtEncoder() {
//        JWK jwk = rsaKeyProperties.toRSAKey();
//        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
//        return new NimbusJwtEncoder(jwkSource);
//    }
}
