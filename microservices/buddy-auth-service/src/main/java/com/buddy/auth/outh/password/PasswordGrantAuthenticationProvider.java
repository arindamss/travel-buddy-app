package com.buddy.auth.outh.password;

import java.time.Instant;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import com.buddy.auth.exception.AuthenticationException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PasswordGrantAuthenticationProvider implements AuthenticationProvider {

    private final AuthenticationManager authenticationManager;

    private final OAuth2TokenGenerator<?> tokenGenerator;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        PasswordGrantAuthenticationToken auth =
                (PasswordGrantAuthenticationToken) authentication;

        UsernamePasswordAuthenticationToken userAuth =
                new UsernamePasswordAuthenticationToken(
                        auth.getUsername(),
                        auth.getPassword());

        Authentication userPrincipal =
                authenticationManager.authenticate(userAuth);

        OAuth2AccessToken accessToken =
                new OAuth2AccessToken(
                        OAuth2AccessToken.TokenType.BEARER,
                        UUID.randomUUID().toString(),
                        Instant.now(),
                        Instant.now().plusSeconds(300));

        return new OAuth2AccessTokenAuthenticationToken(
                null,
                userPrincipal,
                accessToken);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PasswordGrantAuthenticationToken.class.isAssignableFrom(authentication);
    }

}