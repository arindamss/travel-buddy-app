package com.buddy.auth.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.stereotype.Service;

import com.buddy.auth.configuration.RsaKeyProperties;
import com.buddy.auth.configuration.UserDetailsImpl;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

/**
 * Service for generating JSON Web Tokens (JWTs).
 * It uses Spring Security's JwtEncoder to create signed tokens with claims.
 */
@Service
@RequiredArgsConstructor
public class JwtService {

    private final RsaKeyProperties rsaKeyProperties;

    private JwtEncoder jwtEncoder;
    private JwtDecoder jwtDecoder;

    @Value("${treecare.issuer}")
    private String issuer;

    @PostConstruct
    public void init() {
        JWK jwk = rsaKeyProperties.toRSAKey();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        this.jwtEncoder = new NimbusJwtEncoder(jwkSource);
        this.jwtDecoder = NimbusJwtDecoder.withPublicKey(rsaKeyProperties.getPublicKey()).build();
    }

    public String generateAccessToken(Authentication authentication) {
        Instant now = Instant.now();

        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(now.plus(5, ChronoUnit.MINUTES))
                .subject(principal.getUserId().toString())
                .claim("username", principal.getUsername())
                .claim("roles", List.of("USER"))
                .claim("scope", "read write")
                .claim("typ", "access")
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String generateRefreshToken(Authentication authentication) {
        Instant now = Instant.now();

        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(now.plus(60, ChronoUnit.MINUTES))
                .subject(principal.getUserId().toString())
                .claim("typ", "refresh")
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public Jwt decode(String token) {
        return jwtDecoder.decode(token);
    }
}