package com.buddy.post.client.dto.request;

import com.buddy.post.client.dto.enums.MediaType;

import lombok.Data;

@Data
public class PostMediaRequest {
	
	private String objectKey;

    private MediaType mediaType;
}
