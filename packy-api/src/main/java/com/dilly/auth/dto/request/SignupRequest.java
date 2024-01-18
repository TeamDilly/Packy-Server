package com.dilly.auth.dto.request;

import com.dilly.gift.ProfileImage;
import com.dilly.member.Member;
import com.dilly.member.Provider;

public record SignupRequest(
	String provider,
	String nickname,
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
