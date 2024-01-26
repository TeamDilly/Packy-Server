package com.dilly.auth.application;

import static com.dilly.member.Provider.APPLE;
import static com.dilly.member.Provider.KAKAO;
import static com.dilly.member.Provider.TEST;

import com.dilly.auth.AppleAccount;
import com.dilly.auth.KakaoAccount;
import com.dilly.auth.domain.AppleAccountReader;
import com.dilly.auth.domain.AppleAccountWriter;
import com.dilly.auth.domain.KakaoAccountReader;
import com.dilly.auth.domain.KakaoAccountWriter;
import com.dilly.auth.dto.request.SignupRequest;
import com.dilly.auth.dto.response.SignInResponse;
import com.dilly.auth.model.AppleAccountInfo;
import com.dilly.auth.model.AppleToken;
import com.dilly.auth.model.KakaoResource;
import com.dilly.exception.ErrorCode;
import com.dilly.exception.UnsupportedException;
import com.dilly.global.utils.SecurityUtil;
import com.dilly.jwt.JwtService;
import com.dilly.jwt.RefreshToken;
import com.dilly.jwt.domain.JwtReader;
import com.dilly.jwt.domain.JwtWriter;
import com.dilly.jwt.dto.JwtResponse;
import com.dilly.member.Member;
import com.dilly.member.ProfileImage;
import com.dilly.member.Provider;
import com.dilly.member.Status;
import com.dilly.member.domain.MemberReader;
import com.dilly.member.domain.MemberWriter;
import com.dilly.member.domain.ProfileImageReader;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {

	private final JwtService jwtService;
	private final KakaoService kakaoService;
	private final AppleService appleService;
	private final MemberReader memberReader;
	private final MemberWriter memberWriter;
	private final ProfileImageReader profileImageReader;
	private final KakaoAccountReader kakaoAccountReader;
	private final KakaoAccountWriter kakaoAccountWriter;
	private final AppleAccountReader appleAccountReader;
	private final AppleAccountWriter appleAccountWriter;
	private final JwtReader jwtReader;
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
				AppleToken appleToken = appleService.getAppleToken(providerAccessToken);
				AppleAccountInfo appleAccountInfo = appleService.getAppleAccountInfo(appleToken.idToken());
				appleAccountReader.isAppleAccountPresent(appleAccountInfo.sub());

				member = memberWriter.save(signupRequest.toEntity(provider, profileImage));
				appleAccountWriter.save(AppleAccount.builder()
					.id(appleAccountInfo.sub())
					.member(member)
					.refreshToken(appleToken.refreshToken())
					.build()
				);
			}

			case "test" -> {
				provider = TEST;
				member = memberWriter.save(signupRequest.toEntity(provider, profileImage));
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

			case "apple" -> {
				AppleToken appleToken = appleService.getAppleToken(providerAccessToken);
				AppleAccountInfo appleAccountInfo = appleService.getAppleAccountInfo(appleToken.idToken());
				member = appleAccountReader.getMemberById(appleAccountInfo.sub());
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
			case KAKAO -> {
				KakaoAccount kakaoAccount = kakaoAccountReader.findByMember(member);
				kakaoService.unlinkKakaoAccount(kakaoAccount);
				kakaoAccountWriter.delete(kakaoAccount);
			}

			case APPLE -> {
				AppleAccount appleAccount = appleAccountReader.findByMember(member);
				appleService.revokeAppleAccount(appleAccount);
				appleAccountWriter.delete(appleAccount);
			}

			default -> throw new UnsupportedException(ErrorCode.UNSUPPORTED_LOGIN_TYPE);
		}

		RefreshToken refreshToken = jwtReader.findByMember(member);
		jwtWriter.delete(refreshToken);
		member.withdraw();

		return "회원 탈퇴가 완료되었습니다.";
	}
}
