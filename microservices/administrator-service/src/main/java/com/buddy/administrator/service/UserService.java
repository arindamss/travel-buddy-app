package com.buddy.administrator.service;

import org.springframework.http.ResponseEntity;

import com.administrator.client.dto.request.UserCreateRequest;
import com.administrator.client.dto.response.UserCreatedResponse;

public interface UserService {

	public ResponseEntity<UserCreatedResponse> createUser(UserCreateRequest userCreateDto);
	
}
