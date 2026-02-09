package com.buddy.auth.entity;

import java.util.UUID;

import com.buddy.auth.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "userId", "username" }))
public class User {
	
	@Id
	@Column(unique = true, nullable = false)
	private UUID userId;
	
	@Column(unique = true, nullable = false)
	private String username;
	
	@Enumerated(EnumType.STRING)
	private Status status;
}
