package com.dilly.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.dilly.MemberEnumFixture;
import com.dilly.auth.dto.request.SignupRequest;
import com.dilly.auth.dto.response.SignInResponse;
import com.dilly.gift.domain.giftbox.GiftBox;
import com.dilly.gift.domain.giftbox.admin.AdminType;
import com.dilly.global.IntegrationTestSupport;
import com.dilly.global.WithCustomMockUser;
import com.dilly.jwt.RefreshToken;
import com.dilly.jwt.dto.JwtResponse;
import com.dilly.member.domain.Member;
import com.dilly.member.domain.Status;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

class AuthServiceTest extends IntegrationTestSupport {

    private Member NORMAL_MEMBER;
    private final String MEMBER_ID = "1";

    private final String TEST_ACCESS_TOKEN = "test";
    private final SignupRequest TEST_SIGNUP_REQUEST = SignupRequest.builder()
        .provider("test")
        .nickname("test")
        .profileImg(1L)
        .pushNotification(true)
        .marketingAgreement(true)
        .build();

    @BeforeEach
    void setUp() {
        Long memberId = Long.parseLong(MEMBER_ID);
        NORMAL_MEMBER = memberWriter.save(MemberEnumFixture.NORMAL_MEMBER.createMember(memberId));
    }

    @DisplayName("올바르지 않은 Provider Type으로 회원가입을 요청할 경우 예외가 발생한다.")
    @Test
    void throwExceptionWithWrongProviderType() {
        // given
        String providerAccessToken = TEST_ACCESS_TOKEN;
        String wrongProvider = "wrong";
        SignupRequest signupRequest = SignupRequest.builder()
            .provider(wrongProvider)
            .nickname("test")
            .profileImg(1L)
            .pushNotification(true)
            .marketingAgreement(true)
            .build();

        // when // then
        assertThatThrownBy(() -> authService.signUp(providerAccessToken, signupRequest))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("회원가입 시 패키의 선물박스를 받는다.")
    @Test
    void receivePackyGiftBoxAfterSignUp() {
        // given
        GiftBox packyGiftBox = adminGiftBoxReader.findByAdminType(AdminType.ONBOARDING).get()
            .getGiftBox();
        Long receiverBefore = receiverReader.countByGiftBox(packyGiftBox);

        // when
        JwtResponse jwtResponse = authService.signUp(TEST_ACCESS_TOKEN, TEST_SIGNUP_REQUEST);
        Member member = memberReader.findById(jwtResponse.id());

        Long receiverAfter = receiverReader.countByGiftBox(packyGiftBox);
        Long giftBoxCount = receiverReader.countByMember(member);

        // then
        assertThat(receiverAfter).isEqualTo(receiverBefore + 1);
        assertThat(giftBoxCount).isEqualTo(1);
    }

    @DisplayName("로그인 시나리오")
    @TestFactory
    Collection<DynamicTest> signIn() {
        final String PROVIDER = "test";

        return List.of(
            DynamicTest.dynamicTest("회원이 아니라면 NOT_REGISTERED를 반환한다.", () -> {
                // given // when
                SignInResponse signInResponse = authService.signIn(PROVIDER, TEST_ACCESS_TOKEN);

                // then
                assertThat(signInResponse.status()).isEqualTo(Status.NOT_REGISTERED);
            })
            // TODO: 테스트 provider로 회원인 경우 테스트 불가
        );
    }

    @DisplayName("회원탈퇴 시 Refresh token을 hard delete한다.")
    @Test
    @WithCustomMockUser(id = MEMBER_ID)
    void withdraw() {
        // given
        jwtWriter.save(RefreshToken.builder()
            .member(NORMAL_MEMBER)
            .refreshToken("test")
            .build());

        Long refreshTokenBefore = jwtReader.count();

        // when
        authService.withdraw();
        Long refreshTokenAfter = jwtReader.count();

        // then
        assertThat(refreshTokenAfter).isEqualTo(refreshTokenBefore - 1);
    }
}
