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

/**
 * Main Spring Security configuration for the authentication service.
 * Configures the security filter chain, password encoder, JWT encoder/decoder,
 * and authentication manager.
 */
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity // Enable @PreAuthorize, @PostAuthorize, etc.
//public class SecurityConfig {
//
//    private final RsaKeyProperties rsaKeyProperties;
//    private final UserDetailsServiceAdapter userDetailsServiceAdapter;
////    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
////    private final CustomAccessDeniedHandler customAccessDeniedHandler;
//
////    @Value("${spring.security.oauth2.resourceserver.jwt.issuer}")
////    private String issuerUri;
//
//    public SecurityConfig(RsaKeyProperties rsaKeyProperties, UserDetailsServiceAdapter userDetailsServiceAdapter) {
////                          CustomAuthenticationEntryPoint customAuthenticationEntryPoint, CustomAccessDeniedHandler customAccessDeniedHandler) {
//        this.rsaKeyProperties = rsaKeyProperties;
//        this.userDetailsServiceAdapter = userDetailsServiceAdapter;
////        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
////        this.customAccessDeniedHandler = customAccessDeniedHandler;
//    }
//
//    /**
//     * Configures the security filter chain for HTTP requests.
//     * - Disables CSRF protection (stateless JWT doesn't need it).
//     * - Authorizes specific request matchers:
//     * - `/auth/**` (register, login, password reset) are public.
//     * - Other requests require authentication.
//     * - Sets session management to stateless (no sessions, only JWT for auth).
//     * - Configures OAuth2 Resource Server to use JWT.
//     *
//     * @param http The HttpSecurity object to configure.
//     * @return The configured SecurityFilterChain.
//     * @throws Exception if an error occurs during configuration.
//     */
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/auth/**").permitAll()
//                        .anyRequest().authenticated())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .build();
//    }
//    /*
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    	return http
//            .csrf(AbstractHttpConfigurer::disable)
//            .authorizeHttpRequests(auth -> auth
//                // Publicly accessible endpoints
//                .requestMatchers(
//                    "/auth/register",
//                    "/auth/internal/signup",
//                    "/auth/login",
//                    "/auth/password/**"
//                ).permitAll()
//                // All other endpoints require authentication
//                .anyRequest().authenticated()
//            )
//            // Configure the resource server to use JWT and customize its settings
//            .oauth2ResourceServer(oauth2 -> oauth2
//                .jwt(Customizer.withDefaults())
//                // **New addition**: Configure the AuthenticationEntryPoint and AccessDeniedHandler
//                // This is a more explicit way to handle exceptions within the resource server filter chain
//                .authenticationEntryPoint(customAuthenticationEntryPoint)
//                .accessDeniedHandler(customAccessDeniedHandler)
//            )
//            // Set session management to stateless, as we are using JWT tokens
//            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//            // The exception handling is now configured within the oauth2ResourceServer block,
//            // which is a more precise location for a resource server setup
//            .exceptionHandling(exceptions -> exceptions
//                 // This is good practice for non-resource server requests or other errors
//                .authenticationEntryPoint(customAuthenticationEntryPoint)
//                .accessDeniedHandler(customAccessDeniedHandler)
//            )
//            .build();
//
////        return http;
//    } */
//
//    /**
//     * Provides an instance of {@link PasswordEncoder} for hashing passwords.
//     * BCrypt is recommended for strong password hashing.
//     *
//     * @return A BCryptPasswordEncoder instance.
//     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    /**
//     * Configures the {@link AuthenticationManager} using a {@link DaoAuthenticationProvider}.
//     * This manager will use our custom {@link UserDetailsServiceAdapter} and {@link PasswordEncoder}.
//     *
//     * @return An AuthenticationManager instance.
//     */
//    @Bean
//    public AuthenticationManager authenticationManager() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsServiceAdapter);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return new ProviderManager(authProvider);
//    }
//
//    /**
//     * Configures the {@link JwtEncoder} for signing JWTs.
//     * It uses the RSA private key from {@link RsaKeyProperties}.
//     *
//     * @return A NimbusJwtEncoder instance.
//     */
//    @Bean
//    public org.springframework.security.oauth2.jwt.JwtEncoder jwtEncoder() {
//        JWK jwk = rsaKeyProperties.toRSAKey();
//        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
//        return new org.springframework.security.oauth2.jwt.NimbusJwtEncoder(jwkSource);
//    }
//    /*
//    @Bean
//    public JwtEncoder jwtEncoder() {
//        JWK jwk = rsaKeyProperties.toRSAKey();
//        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
//        return new NimbusJwtEncoder(jwkSource);
//    }
//    */
//
//    /**
//     * Configures the {@link JwtDecoder} for verifying JWTs.
//     * It uses the RSA public key from {@link RsaKeyProperties}.
//     * The issuer-uri is also set to validate the token's issuer.
//     *
//     * @return A NimbusJwtDecoder instance.
//     */
//    /*
//    @Bean
//    public JwtDecoder jwtDecoder() {
//        // Build the NimbusJwtDecoder with the public key
//        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withPublicKey(rsaKeyProperties.getPublicKey()).build();
//
//        // Create individual validators
//        OAuth2TokenValidator<Jwt> issuerValidator = new JwtIssuerValidator(issuerUri);
//        // JwtValidators.createDefault() already includes a JwtTimestampValidator
//        // for 'exp' (expiration) and 'nbf' (not before) claims with a default clock skew.
//        OAuth2TokenValidator<Jwt> defaultValidators = JwtValidators.createDefault();
//
//        // Combine all validators using DelegatingOAuth2TokenValidator
//        // The order of validators in the list can be significant if one depends on another.
//        // JwtValidators.createDefault() usually comes first.
//        DelegatingOAuth2TokenValidator<Jwt> allValidators = new DelegatingOAuth2TokenValidator<>(
//                defaultValidators, // Default validators (timestamp, not before)
//                issuerValidator    // Our custom issuer validator
//                // Add any other custom OAuth2TokenValidator instances here if needed
//        );
//
//        jwtDecoder.setJwtValidator(allValidators);
//
//        return jwtDecoder;
//    }
//    */
//}