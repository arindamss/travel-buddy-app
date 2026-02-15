package com.buddy.administrator.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.administrator.client.api.InterestApi;
import com.administrator.client.dto.request.UserInterestRequest;
import com.administrator.client.dto.response.InterestResponse;
import com.administrator.client.dto.response.UserUpdatedResponse;
import com.buddy.administrator.service.InterestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class InterestController implements InterestApi{

	private InterestService interestService;

	@Override
	public ResponseEntity<List<InterestResponse>> getInterests() {
		return ResponseEntity.ok(interestService.getInterests());
	}
	
	@Override
	public ResponseEntity<UserUpdatedResponse> updateInterest(UserInterestRequest request) {
		return ResponseEntity.ok(interestService.updateInterest(request));
	}

}
