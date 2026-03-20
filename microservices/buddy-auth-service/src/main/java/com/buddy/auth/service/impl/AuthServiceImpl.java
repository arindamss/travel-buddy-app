package com.buddy.auth.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.buddy.auth.client.request.LoginRequest;
import com.buddy.auth.client.request.UserCreateRequestDto;
import com.buddy.auth.client.response.AuthResponse;
import com.buddy.auth.client.response.UserCreatedResponseDto;
import com.buddy.auth.entity.User;
import com.buddy.auth.entity.UserCredential;
import com.buddy.auth.enums.CredentialType;
import com.buddy.auth.repository.UserCredentialRepository;
import com.buddy.auth.repository.UserRepository;
import com.buddy.auth.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
	
	private final UserRepository userRepository;
	private final UserCredentialRepository credentialRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
//	private final JwtService jwtService;
//	private final PasswordResetTokenService passwordResetTokenService;
	private final ModelMapper mapper;

	/*
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
	} */
	
	public UserCreatedResponseDto registerUser(UserCreateRequestDto requestDto) {

        if (userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = mapper.map(requestDto, User.class);

        userRepository.save(user);

        UserCredential credential = UserCredential.builder()
                .user(user)
                .secret(passwordEncoder.encode(requestDto.getPassword()))
                .credentialType(CredentialType.valueOf(requestDto.getCredentialType().name()))
                .build();

        credentialRepository.save(credential);

        return UserCreatedResponseDto.builder()
				.username(requestDto.getUsername())
				.status(requestDto.getStatus())
				.credentialType(requestDto.getCredentialType().name())
			.build();
    }

	@Override
	public AuthResponse loginUser(@Valid LoginRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AuthResponse refreshToken(String refreshToken) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
