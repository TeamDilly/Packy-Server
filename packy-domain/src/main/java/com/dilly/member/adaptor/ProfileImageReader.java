package com.dilly.member.adaptor;

import com.dilly.gift.dao.ProfileImageRepository;
import com.dilly.member.ProfileImage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileImageReader {

	private final ProfileImageRepository profileImageRepository;

	public ProfileImage findById(Long profileImageId) {
		return profileImageRepository.findById(profileImageId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로필 이미지입니다."));
	}

	public List<ProfileImage> findAllByOrderBySequenceAsc() {
		return profileImageRepository.findAllByOrderBySequenceAsc();
	}
}
