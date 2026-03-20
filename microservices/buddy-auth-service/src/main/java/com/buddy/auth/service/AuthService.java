package com.buddy.auth.service;

import org.springframework.http.ResponseEntity;

import com.buddy.auth.client.request.LoginRequest;
import com.buddy.auth.client.request.RefreshTokenRequest;
import com.buddy.auth.client.request.UserCreateRequestDto;
import com.buddy.auth.client.response.AuthResponse;
import com.buddy.auth.client.response.UserCreatedResponseDto;

import jakarta.validation.Valid;

public interface AuthService {
	public UserCreatedResponseDto registerUser(UserCreateRequestDto userCreateRequestDto);

	public AuthResponse loginUser(@Valid LoginRequest request);
	
	public AuthResponse refreshToken(String refreshToken);
}
