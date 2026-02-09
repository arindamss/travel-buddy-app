package com.buddy.administrator.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.administrator.client.dto.request.UserCreateRequest;
import com.buddy.administrator.repository.UserRepository;
import com.buddy.administrator.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;

	@Override
	public ResponseEntity<?> createUser(UserCreateRequest userCreateDto) {
		
	}
	
	
}
