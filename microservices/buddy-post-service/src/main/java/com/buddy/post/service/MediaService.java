package com.buddy.post.service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.buddy.post.client.dto.request.GenerateUploadUrlRequest;
import com.buddy.post.client.dto.response.UploadUrlResponse;
import com.buddy.post.config.MinIoConfig;
import com.buddy.post.config.MinioProperties;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.Http.Method;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MediaService {
	
	private final MinioClient minioClient;
	private final MinioProperties properties;

	public UploadUrlResponse generateUploadUrl(GenerateUploadUrlRequest request) {
		
		String extencion = FilenameUtils.getExtension(
					request.getFileName()
				);
		
		String objectKey = 
				"posts/"
				+ LocalDateTime.now()
				+ "/"
				+ UUID.randomUUID()
				+ "."
				+ extencion;
		
		try {
			String url = minioClient.getPresignedObjectUrl(
					GetPresignedObjectUrlArgs.builder()
						.method(Method.PUT)
						.bucket(properties.getBucket())
						.object(objectKey)
						.expiry(10, TimeUnit.MINUTES)
						.build()
					);
			
			return UploadUrlResponse.builder()
                    .uploadUrl(url)
                    .objectKey(objectKey)
                    .build();
		} catch (MinioException e) {
			throw new RuntimeException(e);
		}
	}
}
