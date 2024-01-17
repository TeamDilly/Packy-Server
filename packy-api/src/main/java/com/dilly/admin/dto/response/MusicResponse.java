package com.dilly.admin.dto.response;

import java.util.List;

import lombok.Builder;

@Builder
public record MusicResponse(
	Long id,
	String youtubeUrl,
	List<String> hashtags
) {
}
