package com.buddy.auth.client.request;

import java.util.UUID;

import com.buddy.auth.client.enums.CredentialType;
import com.buddy.auth.client.enums.Status;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateRequestDto {
	
	@NotNull(message = "User Id is required")
	private UUID userId;
	
	@NotNull
	private String username;
	
	@NotNull
	private CharSequence password;
	
	private Status status;
	
	private CredentialType credentialType;
}
