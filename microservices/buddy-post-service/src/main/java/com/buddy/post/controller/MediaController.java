package com.buddy.post.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.buddy.post.client.api.MediaApis;
import com.buddy.post.client.dto.request.GenerateUploadUrlRequest;
import com.buddy.post.client.dto.response.UploadUrlResponse;
import com.buddy.post.service.MediaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MediaController implements MediaApis{
	
	private final MediaService mediaService;

	@Override
	public ResponseEntity<UploadUrlResponse> generateUrl(GenerateUploadUrlRequest request) {
		return new ResponseEntity(mediaService.generateUploadUrl(request), HttpStatus.OK);
	}

}
