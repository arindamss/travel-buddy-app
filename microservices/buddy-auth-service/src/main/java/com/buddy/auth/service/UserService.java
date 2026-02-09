package com.buddy.auth.service;

import org.springframework.http.ResponseEntity;

import com.buddy.auth.client.request.UserCreateRequestDto;

public interface UserService {
	public ResponseEntity<?> signupUser(UserCreateRequestDto userCreateRequestDto);
}
