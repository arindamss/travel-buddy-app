package com.buddy.auth.outh.password;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationConverter;

import jakarta.servlet.http.HttpServletRequest;

public class PasswordGrantAuthenticationConverter implements AuthenticationConverter {
	
	@Override
    public Authentication convert(HttpServletRequest request) {

        String grantType = request.getParameter("grant_type");

        if (!"password".equals(grantType)) {
            return null;
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Authentication clientPrincipal =
                SecurityContextHolder.getContext().getAuthentication();

        return new PasswordGrantAuthenticationToken(
                clientPrincipal,
                null,
                username,
                password,
                null);
    }

}
