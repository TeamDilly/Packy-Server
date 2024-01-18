package com.dilly.member.domain;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dilly.admin.dto.response.ImgResponse;
import com.dilly.gift.ProfileImage;
import com.dilly.gift.dao.ProfileImageRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProfileImageReader {

	private final ProfileImageRepository profileImageRepository;

	public ProfileImage findById(Long profileImageId) {
		return profileImageRepository.findById(profileImageId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로필 이미지입니다."));
	}

	public List<ImgResponse> findAll() {
		return profileImageRepository.findAll().stream()
			.map(profileImage -> ImgResponse.builder()
				.id(profileImage.getId())
				.imgUrl(profileImage.getImgUrl())
				.build()
			)
			.toList();
	}
}
