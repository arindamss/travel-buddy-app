package com.buddy.auth.controller.internal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.buddy.auth.client.api.internal.UserInternalApi;
import com.buddy.auth.client.request.UserCreateRequestDto;
import com.buddy.auth.client.response.UserCreatedResponseDto;
import com.buddy.auth.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController implements UserInternalApi{

	private final AuthService authService;
	
	@Override
	public ResponseEntity<UserCreatedResponseDto> registerUser(@Valid UserCreateRequestDto userCreateRequestDto) {
		// TODO Auto-generated method stub
		return authService.registerUser(userCreateRequestDto);
	}

}
