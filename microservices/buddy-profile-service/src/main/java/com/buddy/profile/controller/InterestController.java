package com.buddy.profile.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.buddy.profile.client.api.InterestApi;
import com.buddy.profile.client.dto.request.UserInterestRequest;
import com.buddy.profile.client.dto.response.InterestResponse;
import com.buddy.profile.client.dto.response.UserUpdatedResponse;
import com.buddy.profile.service.InterestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class InterestController implements InterestApi{

	private final InterestService interestService;

	@Override
	public ResponseEntity<List<InterestResponse>> getInterests() {
		return ResponseEntity.ok(interestService.getInterests());
	}
	
	@Override
	public ResponseEntity<UserUpdatedResponse> updateInterest(UserInterestRequest request) {
		return ResponseEntity.ok(interestService.updateInterest(request));
	}

}
