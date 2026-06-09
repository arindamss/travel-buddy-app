package com.buddy.post.service;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.buddy.post.client.dto.request.CreatePostRequest;
import com.buddy.post.repository.MediaRepository;
import com.buddy.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
	
	private final PostRepository postRepository;
	private final MediaRepository mediaRepository;
	public MultiValueMap createPost(CreatePostRequest createPostRequest) {
		// TODO Auto-generated method stub
		return null;
	}
	
//	public
}
