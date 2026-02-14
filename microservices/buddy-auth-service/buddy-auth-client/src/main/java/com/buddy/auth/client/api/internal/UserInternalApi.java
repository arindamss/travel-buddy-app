package com.buddy.auth.client.api.internal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.buddy.auth.client.request.UserCreateRequestDto;
import com.buddy.auth.client.response.UserCreatedResponseDto;

import jakarta.validation.Valid;

//@RequestMapping("/user")
public interface UserInternalApi {
	
	@PostMapping("/auth/register")
	public ResponseEntity<UserCreatedResponseDto> registerUser(
				@RequestBody @Valid UserCreateRequestDto userCreateRequestDto
			);
}
