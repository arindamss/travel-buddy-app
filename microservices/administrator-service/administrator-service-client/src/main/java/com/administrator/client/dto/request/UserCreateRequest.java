package com.administrator.client.dto.request;


import com.administrator.client.enums.Gender;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreateRequest {
	private String name;
	private float age;
	private Gender gender;
	private long phone;
	private String username;
	private String bio;
	private String profileUrl;
}
