package com.buddy.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.buddy.auth.client.api.AuthControllerApi;
import com.buddy.auth.client.request.LoginRequest;
import com.buddy.auth.client.response.AuthResponse;
import com.buddy.auth.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthControllerApi{
	
	private final AuthService authService;
	
	@Override
	public ResponseEntity<AuthResponse> login(@Valid LoginRequest request) {
		return ResponseEntity.ok(authService.loginUser(request));
	}
	
}