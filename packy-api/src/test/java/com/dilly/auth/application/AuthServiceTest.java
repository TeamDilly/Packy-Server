package com.dilly.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.dilly.auth.dto.request.SignupRequest;
import com.dilly.gift.domain.giftbox.GiftBox;
import com.dilly.gift.domain.giftbox.admin.AdminType;
import com.dilly.global.IntegrationTestSupport;
import com.dilly.jwt.dto.JwtResponse;
import com.dilly.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AuthServiceTest extends IntegrationTestSupport {

    private final String TEST_ACCESS_TOKEN = "test";
    private final SignupRequest TEST_SIGNUP_REQUEST = SignupRequest.builder()
        .provider("test")
        .nickname("test")
        .profileImg(1L)
        .pushNotification(true)
        .marketingAgreement(true)
        .build();

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
}
