package com.buddy.auth.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.buddy.auth.entity.User;
import com.buddy.auth.entity.UserCredential;
import com.buddy.auth.repository.UserCredentialRepository;
import com.buddy.auth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserCredentialRepository credentialRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        UserCredential credential = credentialRepository.findFirstByUser_UserIdOrderByUpdatedOnDesc(user.getUserId())
                .orElseThrow(() ->
                        new UsernameNotFoundException("Credential not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(credential.getSecret())
                .authorities("USER")
                .build();
    }
}
