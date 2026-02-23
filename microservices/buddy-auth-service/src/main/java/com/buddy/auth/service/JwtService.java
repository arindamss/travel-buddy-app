package com.buddy.auth.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
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

    private JwtEncoder jwtEncoder;            // from your SecurityConfig bean
    private JwtDecoder jwtDecoder;

    @Value("${treecare.tokens.acess-ttl-minutes:5}")
    private long accessTtlMinutes;

    @Value("${treecare.tokens.refresh-ttl-minutes:60}")
    private long refreshTtlMinutes;
    
    @Value("${spring.security.oauth2.authorizationserver.issuer}")
	private String issuer;

    @PostConstruct
    public void init() {
        // Encoder
        JWK jwk = rsaKeyProperties.toRSAKey();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        this.jwtEncoder = new NimbusJwtEncoder(jwkSource);

        // Decoder
        this.jwtDecoder = NimbusJwtDecoder.withPublicKey(rsaKeyProperties.getPublicKey()).build();
    }

    public String generateAccessToken(Authentication authentication) {
        Instant now = Instant.now();
        Instant expiry = now.plus(accessTtlMinutes, ChronoUnit.MINUTES);

//        List<String> roles = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());
        UserDetailsImpl principle = (UserDetailsImpl) authentication.getPrincipal();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("http://localhost:8083")
                .issuedAt(now)
                .expiresAt(expiry)
                .subject(principle.getUserId().toString())
                .claim("status", principle.getStatus().name())
                .claim("username", authentication.getName())
                .claim("typ", "access")
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String generateRefreshToken(Authentication authentication) {
        Instant now = Instant.now();
        Instant expiry = now.plus(refreshTtlMinutes, ChronoUnit.MINUTES);
        
        UserDetailsImpl principle = (UserDetailsImpl) authentication.getPrincipal();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("http://localhost:8083")
                .issuedAt(now)
                .expiresAt(expiry)
                .subject(principle.getUserId().toString())
                .claim("typ", "refresh")
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    /**
     * Decode and validate token. If valid, build an Authentication object.
     * Throws JwtException on invalid token.
     */
    public Authentication validateTokenAndGetAuthentication(String token) {
        Jwt jwt = jwtDecoder.decode(token); // will throw JwtException for invalid/expired tokens
        String type = jwt.getClaimAsString("typ");
        if(!"access".equals(type)) {
        	throw new JwtException("Invalid token type");
        }
        String userId = jwt.getSubject();
        String status = jwt.getClaimAsString("status");
//        List<String> roles = jwt.getClaimAsStringList("roles");
//        List<SimpleGrantedAuthority> authorities = (roles == null)
//                ? List.of()
//                : roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        // Create an Authentication (principal=password null) - sufficient for downstream checks
        return new UsernamePasswordAuthenticationToken(userId, null, List.of());
    }
    
    public Jwt decode(String token) {
    	return jwtDecoder.decode(token);
    }

    public boolean isTokenValid(String token) {
        try {
            jwtDecoder.decode(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public long getAccessTokenExpirySeconds() {
        return accessTtlMinutes * 60;
    }
}