package com.dilly.admin;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProfileImageReader {

	private final ProfileImageRepository profileImageRepository;

	public ProfileImage findById(Long profileImageId) {
		return profileImageRepository.findById(profileImageId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로필 이미지입니다."));
	}
}
