package com.buddy.auth.client.response;

import com.buddy.auth.client.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreatedResponseDto {
	private String username;
	private Status status;
	private String credentialType;
}
