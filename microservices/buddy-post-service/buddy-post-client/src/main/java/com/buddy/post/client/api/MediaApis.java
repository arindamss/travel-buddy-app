package com.buddy.post.client.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.buddy.post.client.dto.request.GenerateUploadUrlRequest;
import com.buddy.post.client.dto.response.UploadUrlResponse;

@RequestMapping("/media")
public interface MediaApis {

    @PostMapping("/presigned-url")
    public ResponseEntity<UploadUrlResponse> generateUrl(
            @RequestBody GenerateUploadUrlRequest request
    );
}
