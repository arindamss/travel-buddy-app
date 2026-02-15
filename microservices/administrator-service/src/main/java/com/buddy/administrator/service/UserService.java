package com.buddy.administrator.service;

import org.springframework.http.ResponseEntity;

import com.administrator.client.dto.request.UserCreateRequest;
import com.administrator.client.dto.request.UserInterestRequest;
import com.administrator.client.dto.response.UserCreatedResponse;
import com.administrator.client.dto.response.UserUpdatedResponse;

public interface UserService {

	public ResponseEntity<UserCreatedResponse> createUser(UserCreateRequest userCreateDto);
	
}
