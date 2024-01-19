package com.dilly.auth.application;

import static com.dilly.member.Provider.*;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dilly.auth.domain.KakaoAccountReader;
import com.dilly.auth.domain.KakaoAccountWriter;
import com.dilly.auth.dto.request.SignupRequest;
import com.dilly.auth.dto.response.SignInResponse;
import com.dilly.auth.model.KakaoResource;
import com.dilly.gift.ProfileImage;
import com.dilly.global.exception.UnsupportedException;
import com.dilly.global.response.ErrorCode;
import com.dilly.global.utils.SecurityUtil;
import com.dilly.jwt.JwtService;
import com.dilly.jwt.domain.JwtWriter;
import com.dilly.jwt.dto.JwtResponse;
import com.dilly.member.Member;
import com.dilly.member.Provider;
import com.dilly.member.Status;
import com.dilly.member.domain.MemberReader;
import com.dilly.member.domain.MemberWriter;
import com.dilly.member.domain.ProfileImageReader;

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

			default -> throw new UnsupportedException(ErrorCode.UNSUPPORTED_LOGIN_TYPE);
		}

		return jwtService.issueJwt(member);
	}

	public SignInResponse signIn(String provider, String providerAccessToken) {
		Optional<Member> member;
		switch (provider) {
			case "kakao" -> {
				KakaoResource kakaoResource = kakaoService.getKaKaoAccount(providerAccessToken);
				member = kakaoAccountReader.getMemberById(kakaoResource.getId());
			}

			default -> throw new UnsupportedException(ErrorCode.UNSUPPORTED_LOGIN_TYPE);
		}

		SignInResponse signInResponse;
		if (member.isEmpty()) {
			signInResponse = SignInResponse.builder()
				.status(Status.NOT_REGISTERED)
				.tokenInfo(JwtResponse.builder().build())
				.build();
		} else {
			signInResponse = SignInResponse.builder()
				.status(member.get().getStatus())
				.tokenInfo(jwtService.issueJwt(member.get()))
				.build();
		}

		return signInResponse;
	}

	public String withdraw() {
		Long memberId = SecurityUtil.getMemberId();
		Member member = memberReader.findById(memberId);

		switch (member.getProvider()) {
			case KAKAO -> kakaoService.unlinkKakaoAccount(member);
			default -> throw new UnsupportedException(ErrorCode.UNSUPPORTED_LOGIN_TYPE);
		}

		jwtWriter.delete(memberId);
		member.withdraw();

		return "회원 탈퇴가 완료되었습니다.";
	}
}
