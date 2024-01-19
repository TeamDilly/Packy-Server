package com.dilly.auth.dto.request;

import com.dilly.gift.ProfileImage;
import com.dilly.member.Member;
import com.dilly.member.Provider;

import io.swagger.v3.oas.annotations.media.Schema;

public record SignupRequest(
	@Schema(example = "kakao")
	String provider,
	@Schema(example = "짱제이")
	String nickname,
	@Schema(example = "1")
	Long profileImg,
	Boolean pushNotification,
	Boolean marketingAgreement
) {

	public Member toEntity(Provider provider, ProfileImage profileImage) {
		return Member.builder()
			.provider(provider)
			.nickname(nickname)
			.profileImg(profileImage)
			.pushNotification(pushNotification)
			.marketingAgreement(marketingAgreement)
			.build();
	}
}
