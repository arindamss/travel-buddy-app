package com.buddy.profile.client.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.buddy.profile.client.dto.request.UserCreateRequest;
import com.buddy.profile.client.dto.request.UserInterestRequest;
import com.buddy.profile.client.dto.response.UserCreatedResponse;
import com.buddy.profile.client.dto.response.UserUpdatedResponse;

@RequestMapping("/user")
public interface UserApis {
	
	@PostMapping("/signup")
	public ResponseEntity<UserCreatedResponse> createUser(@RequestBody UserCreateRequest userCreateDto);
}
