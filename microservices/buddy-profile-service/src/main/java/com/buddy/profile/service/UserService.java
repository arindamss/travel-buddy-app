package com.buddy.profile.service;

import org.springframework.http.ResponseEntity;

import com.buddy.profile.client.dto.request.UserCreateRequest;
import com.buddy.profile.client.dto.request.UserInterestRequest;
import com.buddy.profile.client.dto.response.UserCreatedResponse;
import com.buddy.profile.client.dto.response.UserUpdatedResponse;

public interface UserService {

	public ResponseEntity<UserCreatedResponse> createUser(UserCreateRequest userCreateDto);
	
}
