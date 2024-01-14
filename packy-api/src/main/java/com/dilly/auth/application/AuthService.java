package com.dilly.auth.application;

import static com.dilly.member.Provider.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dilly.admin.ProfileImage;
import com.dilly.admin.ProfileImageReader;
import com.dilly.auth.domain.KakaoAccountReader;
import com.dilly.auth.domain.KakaoAccountWriter;
import com.dilly.auth.dto.request.SignupRequest;
import com.dilly.auth.model.KakaoResource;
import com.dilly.global.exception.NotSupportedException;
import com.dilly.global.response.ErrorCode;
import com.dilly.global.utils.SecurityUtil;
import com.dilly.jwt.JwtService;
import com.dilly.jwt.domain.JwtWriter;
import com.dilly.jwt.dto.JwtResponse;
import com.dilly.member.Member;
import com.dilly.member.Provider;
import com.dilly.member.domain.MemberReader;
import com.dilly.member.domain.MemberWriter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {

	private final JwtService jwtService;
	private final KakaoService kakaoService;
	private final MemberReader memberReader;
	private final MemberWriter memberWriter;
	private final ProfileImageReader profileImageReader;
	private final KakaoAccountReader kakaoAccountReader;
	private final KakaoAccountWriter kakaoAccountWriter;
	private final JwtWriter jwtWriter;

	public JwtResponse signUp(String providerAccessToken, SignupRequest signupRequest) {
		Provider provider;
		ProfileImage profileImage = profileImageReader.findById(signupRequest.profileImg());

		Member member = null;
		switch (signupRequest.provider()) {
			case "kakao" -> {
				provider = KAKAO;
				KakaoResource kakaoResource = kakaoService.getKaKaoAccount(providerAccessToken);
				kakaoAccountReader.isKakaoAccountPresent(kakaoResource.getId());

				member = memberWriter.save(signupRequest.toEntity(provider, profileImage));
				kakaoAccountWriter.save(kakaoResource.toEntity(member));
			}

			case "apple" -> {
				provider = APPLE;
			}

			default -> throw new NotSupportedException(ErrorCode.NOT_SUPPORTED_LOGIN_TYPE);
		}

		return jwtService.issueJwt(member);
	}

	public JwtResponse signIn(String provider, String providerAccessToken) {
		Member member = null;
		switch (provider) {
			case "kakao" -> {
				KakaoResource kakaoResource = kakaoService.getKaKaoAccount(providerAccessToken);
				member = kakaoAccountReader.getMemberById(kakaoResource.getId());
			}

			default -> throw new NotSupportedException(ErrorCode.NOT_SUPPORTED_LOGIN_TYPE);
		}

		return jwtService.issueJwt(member);
	}

	public String withdraw() {
		Long memberId = SecurityUtil.getMemberId();
		Member member = memberReader.findById(memberId);

		switch (member.getProvider()) {
			case KAKAO -> kakaoService.unlinkKakaoAccount(member);
			default -> throw new NotSupportedException(ErrorCode.NOT_SUPPORTED_LOGIN_TYPE);
		}

		jwtWriter.delete(memberId);
		member.withdraw();

		return "회원 탈퇴가 완료되었습니다.";
	}
}
