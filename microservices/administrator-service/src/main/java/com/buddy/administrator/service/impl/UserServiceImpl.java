package com.buddy.administrator.service.impl;

import org.springframework.stereotype.Service;

import com.buddy.administrator.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {
	private final UserRepository userRepository;
	
	
}
