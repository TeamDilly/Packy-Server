package com.dilly.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import com.dilly.MemberEnumFixture;
import com.dilly.global.IntegrationTestSupport;
import com.dilly.jwt.dto.JwtResponse;
import com.dilly.member.domain.Member;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

class JwtServiceTest extends IntegrationTestSupport {

    @DisplayName("JWT 발급 시나리오")
    @TestFactory
    Collection<DynamicTest> issueJwt() {
        // given
        Member member = MemberEnumFixture.NORMAL_MEMBER.createMember(1L);

        return List.of(
            DynamicTest.dynamicTest("회원가입 시 Refresh token을 저장한다.", () -> {
                // given
                Long refreshTokenBefore = jwtReader.count();

                // when
                jwtService.issueJwt(member);
                Long refreshTokenAfter = jwtReader.count();

                // then
                assertThat(refreshTokenAfter).isEqualTo(refreshTokenBefore + 1);
            }),
            DynamicTest.dynamicTest("로그인 시 Refresh token을 업데이트한다.", () -> {
                // given
                Long refreshTokenBefore = jwtReader.count();

                // when
                JwtResponse jwtResponse = jwtService.issueJwt(member);
                Long refreshTokenAfter = jwtReader.count();
                RefreshToken refreshToken = jwtReader.findByMember(member);

                // then
                assertThat(refreshTokenAfter).isEqualTo(refreshTokenBefore);
                assertThat(refreshToken.getRefreshToken()).isEqualTo(jwtResponse.refreshToken());
            })
        );
    }
}
