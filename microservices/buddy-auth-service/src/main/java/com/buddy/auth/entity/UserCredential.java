package com.buddy.auth.entity;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.buddy.auth.enums.CredentialType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class UserCredential {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID credentialId;
	
	@OneToOne
	private User user;
	
	@Column(nullable = false)
	private String secret;
	
	@Enumerated(EnumType.STRING)
	private CredentialType credentialType;
	
	@CreationTimestamp
	private Instant createdOn;
	
	@UpdateTimestamp
	private Instant updatedOn;
}
