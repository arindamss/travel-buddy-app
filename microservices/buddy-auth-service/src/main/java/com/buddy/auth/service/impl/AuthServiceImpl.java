package com.buddy.auth.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.buddy.auth.client.request.LoginRequest;
import com.buddy.auth.client.request.UserCreateRequestDto;
import com.buddy.auth.client.response.AuthResponse;
import com.buddy.auth.client.response.UserCreatedResponseDto;
import com.buddy.auth.entity.User;
import com.buddy.auth.entity.UserCredential;
import com.buddy.auth.enums.CredentialType;
import com.buddy.auth.exception.InvalidCredentialsException;
import com.buddy.auth.exception.UserNotFoundException;
import com.buddy.auth.repository.UserCredentialRepository;
import com.buddy.auth.repository.UserRepository;
import com.buddy.auth.service.AuthService;
import com.buddy.auth.service.JwtService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
	
	private final UserRepository userRepository;
	private final UserCredentialRepository credentialRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
//	private final PasswordResetTokenService passwordResetTokenService;
	private final ModelMapper mapper;

	@Override
	public ResponseEntity<UserCreatedResponseDto> registerUser(UserCreateRequestDto requestDto) {
		try {
			
			if(!userRepository.findByUsername(requestDto.getUsername()).isEmpty()) {
				throw new RuntimeException("User with "+requestDto.getUsername()+" username already present.");
			}
			
			User user = mapper.map(requestDto, User.class);
			userRepository.save(user);
			
			UserCredential credential = UserCredential.builder()
						.user(user)
						.secret(passwordEncoder.encode(requestDto.getPassword()))
						.credentialType(CredentialType.valueOf(requestDto.getCredentialType().name()))
					.build();
			credentialRepository.save(credential);
			System.out.println("User: "+user);	
		}
		catch(DataIntegrityViolationException e) {
			throw new RuntimeException("User record vialote unique constrants");
//			e.printStackTrace();
		}
		UserCreatedResponseDto responseDto = UserCreatedResponseDto.builder()
					.username(requestDto.getUsername())
					.status(requestDto.getStatus())
					.credentialType(requestDto.getCredentialType().name())
				.build();
		return new ResponseEntity(responseDto, HttpStatus.CREATED);
	}

	@Override
	public AuthResponse loginUser(@Valid LoginRequest request) {
		try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate both tokens
            String accessToken = jwtService.generateAccessToken(authentication);
            String refreshToken = jwtService.generateRefreshToken(authentication);

            
            // Get user info
            List<User> users = userRepository.findByUsername(request.getUsername());

            if (users.isEmpty()) {
                throw new UserNotFoundException("User not found: " + request.getUsername());
            }

            User user = users.get(0);

            return AuthResponse.builder()
                    .userId(user.getUserId())
                    .username(user.getUsername())
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .tokenType("Bearer")
                    .expiresIn(jwtService.getAccessTokenExpirySeconds())
                    .build();

        } catch (Exception e) {
            throw new InvalidCredentialsException("Invalid user login or password.");
        }
	}

}
