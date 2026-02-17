package com.buddy.profile.entity;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.buddy.profile.enums.Gender;
import com.buddy.profile.enums.Status;
import com.buddy.profile.enums.Style;
import com.buddy.profile.enums.Tag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = "username")
//				@UniqueConstraint(columnNames = "phone")
		}
		
		)
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(updatable = false, nullable = false)
	private UUID id;
	
	@Column(name = "full_name", nullable = false, length = 100)
	private String name;
	
	@Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	private Gender gender;
	
	@Column(nullable = false, length = 20)
	private String phone;
	
	@Column(nullable = false, length = 100)
	private String username;
	
	@Column(length = 500)
	private String bio;
	
	@Column(name = "profile_url")
	private String profileUrl;
	
	private String location;

	@Enumerated(EnumType.STRING)
	private Tag tag;
	
	@Enumerated(EnumType.STRING)
	private Style style;
	
	@Column(name = "status", nullable = false)
	private Status status;
	
	@CreationTimestamp
	@Column(updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
