package com.buddy.profile.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.buddy.profile.client.api.UserApis;
import com.buddy.profile.client.dto.request.UserCreateRequest;
import com.buddy.profile.client.dto.request.UserInterestRequest;
import com.buddy.profile.client.dto.response.UserCreatedResponse;
import com.buddy.profile.client.dto.response.UserUpdatedResponse;
import com.buddy.profile.service.UserService;

@RestController
public class UserController implements UserApis {
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@Override
	public ResponseEntity<UserCreatedResponse> createUser(UserCreateRequest userCreateDto) {
		return userService.createUser(userCreateDto);
	}
	
}
