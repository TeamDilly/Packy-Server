package com.dilly.gift.domain;

import com.dilly.admin.dto.response.MusicResponse;
import com.dilly.gift.Music;
import com.dilly.gift.MusicHashtag;
import com.dilly.gift.dao.MusicRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MusicReader {

	private final MusicRepository musicRepository;

	public List<MusicResponse> findAll() {
        return musicRepository.findAllByOrderBySequenceAsc().stream()
			.map(music -> MusicResponse.builder()
				.id(music.getId())
                .sequence(music.getSequence())
				.youtubeUrl(music.getYoutubeUrl())
				.hashtags(getHashTags(music))
				.build()
			)
			.toList();
	}

	private List<String> getHashTags(Music music) {
		return music.getHashtags().stream()
			.map(MusicHashtag::getHashtag)
			.toList();
	}
}
