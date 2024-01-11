package com.dilly.auth.application;

import static com.dilly.member.LoginType.*;

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
import com.dilly.jwt.JwtService;
import com.dilly.jwt.dto.JwtResponse;
import com.dilly.member.LoginType;
import com.dilly.member.Member;
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
	private final MemberWriter memberWriter;
	private final ProfileImageReader profileImageReader;
	private final KakaoAccountReader kakaoAccountReader;
	private final KakaoAccountWriter kakaoAccountWriter;

	public JwtResponse signUp(String providerAccessToken, SignupRequest signupRequest) {
		LoginType loginType;
		ProfileImage profileImage = profileImageReader.findById(signupRequest.profileImg());

		Member member = null;
		switch (signupRequest.loginType()) {
			case "kakao" -> {
				loginType = KAKAO;
				KakaoResource kakaoResource = kakaoService.getKaKaoAccount(providerAccessToken);
				kakaoAccountReader.isKakaoAccountPresent(kakaoResource.getId());

				member = memberWriter.save(signupRequest.toEntity(loginType, profileImage));
				kakaoAccountWriter.save(kakaoResource.toEntity(member));
			}

			case "apple" -> {
				loginType = APPLE;
			}

			default -> throw new NotSupportedException(ErrorCode.NOT_SUPPORTED_LOGIN_TYPE);
		}

		return jwtService.issueJwt(member);
	}
}
