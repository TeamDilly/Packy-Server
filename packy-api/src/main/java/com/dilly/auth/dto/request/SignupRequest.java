package com.dilly.auth.dto.request;

import com.dilly.admin.ProfileImage;
import com.dilly.member.LoginType;
import com.dilly.member.Member;

public record SignupRequest(
	String loginType,
	String nickname,
	Long profileImg,
	Boolean pushNotification,
	Boolean marketingAgreement
) {

	public Member toEntity(LoginType loginType, ProfileImage profileImage) {
		return Member.builder()
			.loginType(loginType)
			.nickname(nickname)
			.profileImg(profileImage)
			.pushNotification(pushNotification)
			.marketingAgreement(marketingAgreement)
			.build();
	}
}
