package com.buddy.auth.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.buddy.auth.client.request.UserCreateRequestDto;
import com.buddy.auth.entity.User;
import com.buddy.auth.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	
	private final ModelMapper mapper;

	@Override
	public ResponseEntity<?> signupUser(UserCreateRequestDto requestDto) {
		try {
			User user = mapper.map(requestDto, User.class);
			
			System.out.println("User: "+user);
			
					
		}
		catch(DataIntegrityViolationException e) {
			throw new RuntimeException("User record vialote unique constrants");
//			e.printStackTrace();
		}
		return null;
	}

}
