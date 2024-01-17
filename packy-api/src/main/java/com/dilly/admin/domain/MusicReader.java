package com.dilly.admin.domain;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dilly.admin.Music;
import com.dilly.admin.MusicHashtag;
import com.dilly.admin.dao.MusicRepository;
import com.dilly.admin.dto.response.MusicResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MusicReader {

	private final MusicRepository musicRepository;

	public List<MusicResponse> findAll() {
		return musicRepository.findAll().stream()
			.map(music -> MusicResponse.builder()
				.id(music.getId())
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
