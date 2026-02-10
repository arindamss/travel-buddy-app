package com.buddy.auth.client.response;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
//@NoArgsConstructor
@Builder
public class AuthResponse {
	private UUID userId;
    private String username;
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private long expiresIn;
}
