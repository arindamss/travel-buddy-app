package com.buddy.post.entity;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.buddy.post.entity.enums.MediaType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Media {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;
	
	private String mediaUrl;
	
	private MediaType mediaType;
	
	private String thumbnailUrl;
	
	@CreationTimestamp
	private Instant createdAt;
}
