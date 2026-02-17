package com.buddy.profile.service;

import java.util.List;

import com.buddy.profile.client.dto.request.UserInterestRequest;
import com.buddy.profile.client.dto.response.InterestResponse;
import com.buddy.profile.client.dto.response.UserUpdatedResponse;

public interface InterestService {
	public UserUpdatedResponse updateInterest(UserInterestRequest request);

	public List<InterestResponse> getInterests();
}
