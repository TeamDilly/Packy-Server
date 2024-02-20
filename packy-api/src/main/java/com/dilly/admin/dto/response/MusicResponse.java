package com.dilly.admin.dto.response;

import com.dilly.admin.domain.music.Music;
import com.dilly.admin.domain.music.MusicHashtag;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Builder
public record MusicResponse(
	@Schema(example = "1")
	Long id,
	@Schema(example = "www.youtube.com")
	String youtubeUrl,
	@Schema(example = "행복한 날에 어울리는 행복한 음악")
	String title,
	@Schema(example = "1")
	Long sequence,
	List<String> hashtags
) {

	public static MusicResponse from(Music music) {
		return MusicResponse.builder()
			.id(music.getId())
			.youtubeUrl(music.getYoutubeUrl())
			.title(music.getTitle())
			.sequence(music.getSequence())
			.hashtags(music.getHashtags().stream()
				.map(MusicHashtag::getHashtag)
				.toList())
			.build();
	}
}
