package com.buddy.auth.controller.internal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.buddy.auth.client.api.internal.UserInternalApi;
import com.buddy.auth.client.request.UserCreateRequestDto;
import com.buddy.auth.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController implements UserInternalApi{

	private final UserService userService;
	
	@Override
	public ResponseEntity<?> signupUser(@Valid UserCreateRequestDto userCreateRequestDto) {
		// TODO Auto-generated method stub
		userService.signupUser(userCreateRequestDto);
		return null;
	}

}
