package com.buddy.post.client.api;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.buddy.post.client.dto.request.CreatePostRequest;

@RequestMapping("/post")
public interface PostApis {
	
	@PostMapping("/create")
	public ResponseEntity<UUID> createPost(
				@RequestBody CreatePostRequest createPostRequest
			);
}
