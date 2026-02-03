package com.buddy.administrator.service;

import org.springframework.http.ResponseEntity;

import com.administrator.client.dto.request.UserCreateRequest;

public interface UserService {

	public ResponseEntity<?> createUser(UserCreateRequest userCreateDto);
	
}
