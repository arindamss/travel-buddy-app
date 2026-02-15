package com.buddy.administrator.service;

import java.util.List;

import com.administrator.client.dto.request.UserInterestRequest;
import com.administrator.client.dto.response.InterestResponse;
import com.administrator.client.dto.response.UserUpdatedResponse;

public interface InterestService {
	public UserUpdatedResponse updateInterest(UserInterestRequest request);

	public List<InterestResponse> getInterests();
}
