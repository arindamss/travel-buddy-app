package com.buddy.profile.client.dto.request;


import java.util.List;

import com.buddy.profile.client.enums.Gender;
import com.buddy.profile.client.enums.Style;
import com.buddy.profile.client.enums.Tag;

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
	private String password;
	private String bio;
	private String profileUrl;
	private String location;
	private List<String> interests;
	private Tag tag;
	private Style style;
}
