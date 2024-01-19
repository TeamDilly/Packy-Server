package com.dilly.admin.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record MusicResponse(
	@Schema(example = "1")
	Long id,
	@Schema(example = "www.youtube.com")
	String youtubeUrl,
	List<String> hashtags
) {
}
