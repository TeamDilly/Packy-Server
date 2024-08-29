package com.dilly.auth.application;

import com.dilly.admin.adaptor.AdminGiftBoxReader;
import com.dilly.auth.adaptor.KakaoAccountReader;
import com.dilly.auth.adaptor.KakaoAccountWriter;
import com.dilly.auth.application.strategy.AuthActionProvider;
import com.dilly.auth.application.strategy.AuthStrategy;
import com.dilly.auth.domain.KakaoAccount;
import com.dilly.auth.dto.request.SignupRequest;
import com.dilly.auth.dto.response.SignInResponse;
import com.dilly.gift.adaptor.ReceiverWriter;
import com.dilly.gift.domain.giftbox.GiftBox;
import com.dilly.gift.domain.giftbox.admin.AdminGiftBox;
import com.dilly.gift.domain.giftbox.admin.AdminType;
import com.dilly.jwt.JwtService;
import com.dilly.jwt.RefreshToken;
import com.dilly.jwt.adaptor.JwtReader;
import com.dilly.jwt.adaptor.JwtWriter;
import com.dilly.jwt.dto.JwtResponse;
import com.dilly.member.adaptor.MemberReader;
import com.dilly.member.adaptor.ProfileImageReader;
import com.dilly.member.application.MemberService;
import com.dilly.member.domain.Member;
import com.dilly.member.domain.ProfileImage;
import com.dilly.member.domain.Provider;
import com.dilly.member.domain.Status;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {

	@Value("${security.oauth2.provider.kakao.admin-key}")
	private String kakaoAdminKey;

	private final AuthActionProvider authActionProvider;

	private final MemberService memberService;
	private final JwtService jwtService;

	private final MemberReader memberReader;
	private final ProfileImageReader profileImageReader;
	private final JwtReader jwtReader;
	private final JwtWriter jwtWriter;
	private final AdminGiftBoxReader adminGiftBoxReader;
	private final ReceiverWriter receiverWriter;

	private final KakaoAccountReader kakaoAccountReader;
	private final KakaoAccountWriter kakaoAccountWriter;

	public JwtResponse signUp(String providerAccessToken, SignupRequest signupRequest) {
		ProfileImage profileImage = profileImageReader.findById(signupRequest.profileImg());

		Provider provider = Provider.valueOf(signupRequest.provider().toUpperCase());
		final AuthStrategy authStrategy = authActionProvider.getStrategy(provider);
		Member member = authStrategy.signUp(providerAccessToken, signupRequest, profileImage);

		sendOnboardingGiftBox(member);

		return jwtService.issueJwt(member);
	}

	private void sendOnboardingGiftBox(Member member) {
		Optional<AdminGiftBox> adminGiftBox = adminGiftBoxReader.findByAdminType(
			AdminType.ONBOARDING);

		if (adminGiftBox.isPresent()) {
			GiftBox onboardingGiftBox = adminGiftBox.get().getGiftBox();
			receiverWriter.save(member, onboardingGiftBox);
		}
	}

	public SignInResponse signIn(String provider, String providerAccessToken) {
		Provider providerType = Provider.valueOf(provider.toUpperCase());
		final AuthStrategy authStrategy = authActionProvider.getStrategy(providerType);

		Optional<Member> member = authStrategy.signIn(providerAccessToken);

		SignInResponse signInResponse;
		if (member.isEmpty()) {
			signInResponse = SignInResponse.from(Status.NOT_REGISTERED);
		} else {
			signInResponse = SignInResponse.from(member.get().getStatus(),
				member.get().getNickname(),
				jwtService.issueJwt(member.get()));
		}

		return signInResponse;
	}

	public String withdraw() {
		Member member = memberService.getMember();

		final AuthStrategy authStrategy = authActionProvider.getStrategy(member.getProvider());
		authStrategy.withdraw(member);

		RefreshToken refreshToken = jwtReader.findByMember(member);
		jwtWriter.delete(refreshToken);
		member.withdraw();

		return "회원 탈퇴가 완료되었습니다.";
	}

	public String externalWithdraw(String authorization, String userId) {
		Optional<Member> member = kakaoAccountReader.findMemberById(userId);

		boolean canWithdraw = (authorization.equals(kakaoAdminKey) && member.isPresent());
		if (canWithdraw) {
			KakaoAccount kakaoAccount = kakaoAccountReader.findByMember(member.get());
			kakaoAccountWriter.delete(kakaoAccount);

			RefreshToken refreshToken = jwtReader.findByMember(member.get());
			jwtWriter.delete(refreshToken);

			member.get().withdraw();
		}

		return "외부 회원 탈퇴가 완료되었습니다.";
	}
}
