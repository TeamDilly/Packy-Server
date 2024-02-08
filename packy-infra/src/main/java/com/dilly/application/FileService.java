package com.dilly.application;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.dilly.dto.request.FileRequest;
import com.dilly.exception.ErrorCode;
import com.dilly.exception.internalserver.InternalServerException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileService {

	@Value("${cloud.s3.bucket}")
	private String bucket;

	private final AmazonS3 amazonS3;

	public FileRequest getPresignedUrl(String prefix, String fileName) {
		if (!prefix.isEmpty()) {
			fileName = createPath(prefix, fileName);
		}

		GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePresignedUrlRequest(bucket, fileName);
		URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

		return FileRequest.builder()
			.url(url.toString())
			.build();
	}

	private GeneratePresignedUrlRequest getGeneratePresignedUrlRequest(String bucket, String fileName) {
		try {
			ResponseHeaderOverrides headerOverrides = new ResponseHeaderOverrides()
				.withCacheControl("No-cache")
				.withContentDisposition(
					"attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
			GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
				bucket, fileName)
				.withMethod(HttpMethod.PUT)
				.withExpiration(getPresignedUrlExpiration())
				.withResponseHeaders(headerOverrides);

			generatePresignedUrlRequest.addRequestParameter(
				Headers.S3_CANNED_ACL,
				CannedAccessControlList.PublicRead.toString()
			);

			return generatePresignedUrlRequest;

		} catch (Exception e) {
			throw new InternalServerException(ErrorCode.FILE_SERVER_ERROR);
		}
	}

	private Date getPresignedUrlExpiration() {
		Date expiration = new Date();
		long expTimeMillis = expiration.getTime();
		expTimeMillis += 1000 * 60 * 2;
		expiration.setTime(expTimeMillis);

		return expiration;
	}

	private String createFileId() {
		return UUID.randomUUID().toString();
	}

	private String createPath(String prefix, String fileName) {
		String fileId = createFileId();
		return String.format("%s/%s", prefix, fileId + "-" + fileName);
	}
}
