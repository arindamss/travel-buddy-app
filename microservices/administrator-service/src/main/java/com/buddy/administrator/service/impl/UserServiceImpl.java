package com.buddy.administrator.service.impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.administrator.client.dto.request.UserCreateRequest;
import com.administrator.client.dto.request.UserInterestRequest;
import com.administrator.client.dto.response.UserCreatedResponse;
import com.administrator.client.dto.response.UserUpdatedResponse;
import com.buddy.administrator.entity.Interest;
import com.buddy.administrator.entity.User;
import com.buddy.administrator.entity.UserInterest;
import com.buddy.administrator.enums.Status;
import com.buddy.administrator.exception.InternalServerError;
import com.buddy.administrator.exception.UserAlreadyPresentException;
import com.buddy.administrator.exception.UserNotFoundException;
import com.buddy.administrator.repository.InterestRepository;
import com.buddy.administrator.repository.UserInterestRepository;
import com.buddy.administrator.repository.UserRepository;
import com.buddy.administrator.service.BloomFilterService;
import com.buddy.administrator.service.UserService;
import com.buddy.auth.client.enums.CredentialType;
//import com.buddy.auth.client.enums.Status;
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
	public ResponseEntity<UserCreatedResponse> createUser(UserCreateRequest userCreateDto) {
		if(bloom.mightContainUsername(userCreateDto.getUsername())) {
			if(userRepository.existsByUsername(userCreateDto.getUsername())) {
				throw new UserAlreadyPresentException("Username is already present. try to create user with different username.", HttpStatus.CONFLICT);
			}
		}
		
		User user = modelMapper.map(userCreateDto, User.class);
		user.setStatus(Status.PENDING);
		System.out.println("User: "+user);
		
		userRepository.save(user);
		
		bloom.addUsername(user.getUsername());
		
		UserCreateRequestDto createRequestDto = UserCreateRequestDto.builder()
				.userId(user.getId())
				.username(user.getUsername())
				.password(userCreateDto.getPassword().toString())
				.status(com.buddy.auth.client.enums.Status.ACTIVE)
				.credentialType(CredentialType.PASSWORD)
				.build();
		try {
			authClient.registerUser(createRequestDto);			
		}
		catch(Exception e) {
			userRepository.deleteById(user.getId());
			e.printStackTrace();
			throw new InternalServerError("Failed to save user credential.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		user.setStatus(Status.ACTIVE);
		userRepository.save(user);
		
		UserCreatedResponse response = modelMapper.map(user, UserCreatedResponse.class);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}	
	
}
