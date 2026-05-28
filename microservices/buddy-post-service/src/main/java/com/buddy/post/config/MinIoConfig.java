package com.buddy.post.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MinIoConfig {

	private final MinioProperties properties;
	
	@Bean
	public MinioClient minioClient() {
		return MinioClient.builder()
				.endpoint(properties.getUrl())
				.credentials(properties.getAccessKey(), properties.getSecretKey())
				.build();
	}
}
