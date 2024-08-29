package com.dilly.member.application;

import static com.dilly.MemberEnumFixture.NORMAL_MEMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.dilly.exception.authorizationfailed.AuthorizationFailedException;
import com.dilly.global.IntegrationTestSupport;
import com.dilly.global.WithCustomMockUser;
import com.dilly.jwt.RefreshToken;
import com.dilly.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberServiceTest extends IntegrationTestSupport {

    private Member member;

    private final String MEMBER_ID = "1";

    @BeforeEach
    void setUp() {
        Long memberId = Long.parseLong(MEMBER_ID);
        member = memberWriter.save(NORMAL_MEMBER.createMember(memberId));
    }

    @DisplayName("유저의 엔티티를 조회한다")
    @Test
    @WithCustomMockUser(id = MEMBER_ID)
    void getMember() {
        // when
        Member result = memberService.getMember();

        // then
        assertThat(result).isEqualTo(member);
    }

    @DisplayName("탈퇴한 유저의 엔티티를 조회할 경우 예외를 던진다")
    @Test
    @WithCustomMockUser(id = MEMBER_ID)
    void getMemberThrowExceptionForWithdrawnMember() {
        // given
        jwtWriter.save(RefreshToken.builder()
                .member(member)
                .refreshToken("test")
                .build());

        authService.withdraw();

        // when // then
        assertThatThrownBy(() -> memberService.getMember())
            .isInstanceOf(AuthorizationFailedException.class);
    }
}
