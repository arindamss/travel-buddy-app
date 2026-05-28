package com.buddy.post.client.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateUploadUrlRequest {

    private String fileName;

    private String contentType;
}