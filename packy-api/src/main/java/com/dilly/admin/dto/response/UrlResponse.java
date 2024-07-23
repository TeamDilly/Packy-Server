package com.dilly.admin.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record UrlResponse(
	@Schema(example = "www.example.com")
	String url
) {

	public static UrlResponse from(String url) {
		return UrlResponse.builder()
			.url(url)
			.build();
	}
}
