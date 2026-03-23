package com.buddy.auth.service.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.buddy.auth.client.request.LoginRequest;
import com.buddy.auth.client.request.UserCreateRequestDto;
import com.buddy.auth.client.response.AuthResponse;
import com.buddy.auth.client.response.UserCreatedResponseDto;
import com.buddy.auth.configuration.UserDetailsImpl;
import com.buddy.auth.entity.User;
import com.buddy.auth.entity.UserCredential;
import com.buddy.auth.enums.CredentialType;
import com.buddy.auth.repository.UserCredentialRepository;
import com.buddy.auth.repository.UserRepository;
import com.buddy.auth.service.AuthService;
import com.buddy.auth.service.JwtService;

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
    public AuthResponse loginUser(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String accessToken = jwtService.generateAccessToken(authentication);
        String refreshToken = jwtService.generateRefreshToken(authentication);

        User user = userRepository.findByUsername(request.getUsername())
                .stream()
                .findFirst()
                .orElseThrow();

        return AuthResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(300)
                .build();
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {

        Jwt jwt = jwtService.decode(refreshToken);

        if (!"refresh".equals(jwt.getClaimAsString("typ"))) {
            throw new RuntimeException("Invalid refresh token");
        }

        UUID userId = UUID.fromString(jwt.getSubject());

        User user = userRepository.findById(userId).orElseThrow();

        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                List.of()
        );

        String newAccessToken = jwtService.generateAccessToken(auth);

        return AuthResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(300)
                .build();
    }

}
