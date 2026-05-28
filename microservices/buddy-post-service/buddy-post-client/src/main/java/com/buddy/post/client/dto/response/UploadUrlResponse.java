package com.buddy.post.client.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UploadUrlResponse {

    private String uploadUrl;

    private String objectKey;
}
