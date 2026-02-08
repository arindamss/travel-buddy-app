package com.buddy.auth.client.api.internal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.buddy.auth.client.request.UserCreateRequestDto;

import jakarta.validation.Valid;

//@RequestMapping("/user")
public interface UserInternalApi {
	
	@PostMapping("/user/signup")
	public ResponseEntity<?> signupUser(
				@RequestBody @Valid UserCreateRequestDto userCreateRequestDto
			);
}
