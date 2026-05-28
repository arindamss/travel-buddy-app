package com.buddy.post.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.buddy.post.client.api.PostApis;
import com.buddy.post.client.dto.request.CreatePostRequest;
import com.buddy.post.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PostController implements PostApis {

	private final PostService postService;
	
	@Override
	public ResponseEntity<UUID> createPost(CreatePostRequest createPostRequest) {
		return new ResponseEntity(postService.createPost(createPostRequest), HttpStatus.OK);
	}

}
