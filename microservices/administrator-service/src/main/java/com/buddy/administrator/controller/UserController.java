package com.buddy.administrator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.administrator.client.api.UserApis;
import com.administrator.client.dto.request.UserCreateRequest;
import com.buddy.administrator.service.UserService;

@RestController
public class UserController implements UserApis {
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@Override
	public ResponseEntity<?> createUser(UserCreateRequest userCreateDto) {
		return userService.createUser(userCreateDto);
	}
	
}
