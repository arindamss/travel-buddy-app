package com.buddy.auth.outh.password;

import java.util.Map;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

public class PasswordGrantAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {
	private final String username;
    private final String password;

    public PasswordGrantAuthenticationToken(
            Authentication clientPrincipal,
            Set<String> scopes,
            String username,
            String password,
            Map<String, Object> additionalParameters) {

        super(new AuthorizationGrantType("password"), clientPrincipal, additionalParameters);

        this.username = username;
        this.password = password;
    }
    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
