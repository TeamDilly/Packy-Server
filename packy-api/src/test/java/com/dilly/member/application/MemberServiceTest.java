package com.dilly.member.application;

import static com.dilly.MemberEnumFixture.NORMAL_MEMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.dilly.exception.AuthorizationFailedException;
import com.dilly.global.IntegrationTestSupport;
import com.dilly.global.WithCustomMockUser;
import com.dilly.jwt.RefreshToken;
import com.dilly.member.domain.Device;
import com.dilly.member.domain.Member;
import com.dilly.member.domain.Platform;
import com.dilly.member.dto.request.FCMTokenRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MemberServiceTest extends IntegrationTestSupport {

    private Member member;
    private Member member2;

    private final String MEMBER_ID = "1";
    private final String MEMBER2_ID = "2";

    @BeforeEach
    void setUp() {
        Long memberId = Long.parseLong(MEMBER_ID);
        member = memberWriter.save(NORMAL_MEMBER.createMember(memberId));

        Long member2Id = Long.parseLong(MEMBER2_ID);
        member2 = memberWriter.save(NORMAL_MEMBER.createMember(member2Id));
    }

    @AfterEach
    void tearDown() {
        memberWriter.deleteAll();
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

    @Nested
    @DisplayName("FCM 토큰을 저장할 때")
    class IssueFcmToken {

        String deviceId = "test";
        String fcmToken = "test";
        String platform = "IOS";

        @AfterEach
        void tearDown() {
            deviceWriter.deleteAll();
        }

        @DisplayName("해당 기기에 대한 Device 엔티티가 없으면 새로 생성한다")
        @Test
        @WithCustomMockUser(id = MEMBER_ID)
        void createDevice() {
            // given
            FCMTokenRequest fcmTokenRequest = FCMTokenRequest.builder()
                .deviceId(deviceId)
                .fcmToken(fcmToken)
                .platform(platform)
                .build();

            Long deviceBefore = deviceReader.countByDeviceId(deviceId);

            // when
            memberService.issueFcmToken(fcmTokenRequest);
            Long deviceAfter = deviceReader.countByDeviceId(deviceId);

            // then
            assertThat(deviceBefore).isZero();
            assertThat(deviceAfter).isEqualTo(1);
        }

        @Nested
        @DisplayName("해당 기기에 대한 Device 엔티티가 이미 존재할 때")
        class DeviceAlreadyExists {

            @DisplayName("저장된 유저와 새로운 유저가 다를 경우 유저 정보를 업데이트한다")
            @Test
            @WithCustomMockUser(id = MEMBER2_ID)
            void updateMember() {
                // given
                Device device = deviceWriter.save(deviceId, fcmToken, Platform.valueOf(platform),
                    member);
                FCMTokenRequest fcmTokenRequest = FCMTokenRequest.builder()
                    .deviceId(deviceId)
                    .fcmToken(fcmToken)
                    .platform(platform)
                    .build();

                Member deviceMemberBefore = device.getMember();

                // when
                memberService.issueFcmToken(fcmTokenRequest);
                Member deviceMemberAfter = device.getMember();

                // then
                assertThat(deviceMemberBefore).isEqualTo(member);
                assertThat(deviceMemberAfter).isEqualTo(member2);
            }

            @DisplayName("저장된 유저와 새로운 유저가 같을 경우 FCM 토큰을 업데이트한다")
            @Test
            @WithCustomMockUser(id = MEMBER_ID)
            void updateFcmToken() {
                // given
                Device device = deviceWriter.save(deviceId, fcmToken, Platform.valueOf(platform),
                    member);

                String newFcmToken = "newToken";
                FCMTokenRequest fcmTokenRequest = FCMTokenRequest.builder()
                    .deviceId(deviceId)
                    .fcmToken(newFcmToken)
                    .platform(platform)
                    .build();

                String deviceFcmTokenBefore = device.getFcmToken();

                // when
                memberService.issueFcmToken(fcmTokenRequest);

                String deviceFcmTokenAfter = device.getFcmToken();

                // then
                assertThat(deviceFcmTokenBefore).isEqualTo(fcmToken);
                assertThat(deviceFcmTokenAfter).isEqualTo(newFcmToken);

            }
        }
    }
}
