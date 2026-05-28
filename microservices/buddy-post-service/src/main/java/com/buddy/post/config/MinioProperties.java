package com.buddy.post.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {
	private String url;
	private String accessKey;
	private String secretKey;
	private String bucket;
}
