package com.buddy.post.service;

import com.buddy.post.client.dto.request.GenerateUploadUrlRequest;
import com.buddy.post.client.dto.response.UploadUrlResponse;
import com.buddy.post.config.MinioProperties;
import com.buddy.post.entity.MediaMetaData;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.Http.Method;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MediaService {
	
	private final MinioClient minioClient;
	private final MinioProperties properties;

	private final MetaDataService metaDataService;

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

			MediaMetaData mediaMetaData = MediaMetaData.builder()
					.objectKey(objectKey)
					.userId(UUID.randomUUID())
					.used(Boolean.FALSE)
					.build();

			metaDataService.saveMediaMetaData(mediaMetaData);
			
			return UploadUrlResponse.builder()
                    .uploadUrl(url)
                    .objectKey(objectKey)
                    .build();
		} catch (MinioException e) {
			throw new RuntimeException(e);
		}
	}
}
