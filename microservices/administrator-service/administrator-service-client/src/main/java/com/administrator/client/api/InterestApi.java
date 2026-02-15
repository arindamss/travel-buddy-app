package com.administrator.client.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.administrator.client.dto.request.UserInterestRequest;
import com.administrator.client.dto.response.InterestResponse;
import com.administrator.client.dto.response.UserUpdatedResponse;

@RequestMapping("/interest")
public interface InterestApi {
	
	@GetMapping
	public ResponseEntity<List<InterestResponse>> getInterests();
	
	@PatchMapping("/user")
	public ResponseEntity<UserUpdatedResponse> updateInterest(@RequestBody UserInterestRequest request);
}
