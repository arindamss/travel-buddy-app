package com.administrator.client.dto.response;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import com.administrator.client.enums.Gender;
import com.administrator.client.enums.Status;
import com.administrator.client.enums.Style;
import com.administrator.client.enums.Tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdatedResponse {
	private UUID id;
    private String name;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String phone;
    private String username;
    private String bio;
    private String profileUrl;
    private String location;
    private Tag tag;
    private Style style;
    private Status status;
    private Instant updatedAt;
}
