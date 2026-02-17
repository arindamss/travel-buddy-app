package com.buddy.profile.client.dto.response;

import com.buddy.profile.client.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreatedResponse {
	private String username;
	private Status status;
}
