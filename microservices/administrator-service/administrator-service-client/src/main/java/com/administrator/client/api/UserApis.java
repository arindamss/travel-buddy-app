package com.administrator.client.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.administrator.client.dto.request.UserCreateRequest;

@RequestMapping("/user")
public interface UserApis {
	
	@PostMapping("/signup")
	public ResponseEntity<?> createUser(@RequestBody UserCreateRequest userCreateDto);
}
