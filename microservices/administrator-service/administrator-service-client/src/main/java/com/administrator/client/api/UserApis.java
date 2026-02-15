package com.administrator.client.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.administrator.client.dto.request.UserCreateRequest;
import com.administrator.client.dto.request.UserInterestRequest;
import com.administrator.client.dto.response.UserCreatedResponse;
import com.administrator.client.dto.response.UserUpdatedResponse;

@RequestMapping("/user")
public interface UserApis {
	
	@PostMapping("/signup")
	public ResponseEntity<UserCreatedResponse> createUser(@RequestBody UserCreateRequest userCreateDto);
}
