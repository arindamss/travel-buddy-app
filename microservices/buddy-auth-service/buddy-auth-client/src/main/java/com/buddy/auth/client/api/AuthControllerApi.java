package com.buddy.auth.client.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.buddy.auth.client.request.LoginRequest;
import com.buddy.auth.client.response.AuthResponse;

import jakarta.validation.Valid;

@RequestMapping("/auth")
public interface AuthControllerApi {
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest);
}
