package com.buddy.administrator.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.administrator.client.dto.request.UserCreateRequest;
import com.buddy.administrator.entity.User;
import com.buddy.administrator.exception.UserAlreadyPresentException;
import com.buddy.administrator.repository.UserRepository;
import com.buddy.administrator.service.BloomFilterService;
import com.buddy.administrator.service.UserService;
import com.buddy.auth.client.enums.Status;
import com.buddy.auth.client.request.UserCreateRequestDto;
import com.buddy.client.auth.AuthClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final BloomFilterService bloom;
	
	private final AuthClient authClient;
	
	private final ModelMapper modelMapper;

	@Override
	public ResponseEntity<?> createUser(UserCreateRequest userCreateDto) {
		if(bloom.mightContainUsername(userCreateDto.getUsername())) {
			if(userRepository.existsByUsername(userCreateDto.getUsername())) {
				throw new UserAlreadyPresentException("Username is already present. try to create user with different username.", HttpStatus.CONFLICT);
			}
		}
		
		User user = modelMapper.map(userCreateDto, User.class);
		System.out.println("User: "+user);

		userRepository.save(user);
		
		bloom.addUsername(user.getUsername());
		
		UserCreateRequestDto createRequestDto = UserCreateRequestDto.builder()
				.userId(user.getId())
				.username(user.getUsername())
				.password(userCreateDto.getPassword().toString())
				.status(Status.ACTIVE)
				.build();
		authClient.signupUser(createRequestDto);
		
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}
	
	
	
	
}
