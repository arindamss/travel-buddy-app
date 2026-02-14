package com.buddy.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.web.webauthn.management.UserCredentialRepository;
import org.springframework.stereotype.Service;

import com.buddy.auth.configuration.UserDetailsImpl;
import com.buddy.auth.entity.User;
import com.buddy.auth.entity.UserCredential;
import com.buddy.auth.repository.UserCredentialRepository;
import com.buddy.auth.repository.UserRepository;


/**
 * Custom implementation of Spring Security's {@link UserDetailsService}.
 * This service is responsible for loading user-specific data during the authentication process.
 * It retrieves user details and their credentials from the database.
 */
@Service
public class UserDetailsServiceAdapter implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserCredentialRepository credentialRepository;

    public UserDetailsServiceAdapter(UserRepository userRepository, UserCredentialRepository credentialRepository) {
        this.userRepository = userRepository;
        this.credentialRepository = credentialRepository;
    }

    /**
     * Locates the user based on the username (userLogin in our case).
     * In the actual implementation, the search for the user can be customized,
     * such as looking up by email, phone, or a unique login ID.
     *
     * @param userLogin The username (userLogin) identifying the user whose data is required.
     * @return A fully populated user record (an instance of {@link UserDetails})
     * @throws UsernameNotFoundException if the user could not be found or has no associated credentials.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).stream().findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        UserCredential credential = credentialRepository.findFirstByUser_UserIdOrderByUpdatedOnDesc(user.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("Credentials not found for user: " + username));

        return new UserDetailsImpl(user, credential.getSecret());
    }
}