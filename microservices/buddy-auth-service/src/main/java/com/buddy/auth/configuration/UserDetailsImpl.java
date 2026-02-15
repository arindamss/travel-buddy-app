package com.buddy.auth.configuration;


import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.buddy.auth.entity.User;
import com.buddy.auth.enums.Status;

import lombok.Getter;

/**
 * Custom implementation of Spring Security's UserDetails interface.
 * This class adapts our User entity to the UserDetails contract,
 * providing the necessary user information for authentication and authorization.
 */
@Getter
public class UserDetailsImpl implements UserDetails {

    private final UUID userId;
    private final String username;
    private final String password; // Stored hashed password
    private final Status status;
    
    public UserDetailsImpl(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.password = null; // not needed for refresh token
        this.status = user.getStatus();
    }

    public UserDetailsImpl(User user, String hashedPassword) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.password = hashedPassword;
        this.status = user.getStatus();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // Account status methods (customize as needed for your application's logic)
    @Override
    public boolean isAccountNonExpired() {
        return true; // Assume account never expires
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Assume account is never locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Assume credentials never expire
    }

    @Override
    public boolean isEnabled() {
        return true; // Assume user is always enabled
    }
}