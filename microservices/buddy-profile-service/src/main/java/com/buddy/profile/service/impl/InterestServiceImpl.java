package com.buddy.profile.service.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.buddy.profile.client.dto.request.UserInterestRequest;
import com.buddy.profile.client.dto.response.InterestResponse;
import com.buddy.profile.client.dto.response.UserUpdatedResponse;
import com.buddy.profile.entity.Interest;
import com.buddy.profile.entity.User;
import com.buddy.profile.entity.UserInterest;
import com.buddy.profile.exception.UserNotFoundException;
import com.buddy.profile.repository.InterestRepository;
import com.buddy.profile.repository.UserInterestRepository;
import com.buddy.profile.repository.UserRepository;
import com.buddy.profile.service.InterestService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InterestServiceImpl implements InterestService{
	
	private final UserRepository userRepository;
	private final InterestRepository interestRepository;
	private final UserInterestRepository userInterestRepository;
	
	private final ModelMapper modelMapper;

	@Override
	public List<InterestResponse> getInterests() {
		try {
			List<Interest> intersts = interestRepository.findAll();
			
			List<InterestResponse> respons = intersts.stream()
									.map(ins -> modelMapper.map(ins, InterestResponse.class))
									.toList();
			return respons;
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Exception While Fetching Intersts");
		}
	}
	

	@Override
	public UserUpdatedResponse updateInterest(UserInterestRequest request) {
		
		User user = userRepository.findById(request.getUserId())
				.stream()
				.findFirst()
				.orElseThrow(() -> new UserNotFoundException("User not found", HttpStatus.NOT_FOUND));
		
		for(UUID interestId : request.getInterestIds()) {
			Interest interest = interestRepository.findById(interestId)
									.orElseThrow(() -> new RuntimeException("Interest Not found."));
			
			UserInterest userInterest = UserInterest.builder()
						.user(user)
						.interest(interest)
					.build();
			userInterestRepository.save(userInterest);
		}
		
		UserUpdatedResponse response = modelMapper.map(user, UserUpdatedResponse.class);
		return response;
		
	}
}
