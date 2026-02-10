package com.buddy.auth.client.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
	@NotBlank(message = "User login cannot be blank")
	private String username;
	
	@NotBlank(message = "Password cannot be blank")
	private String password;
}
