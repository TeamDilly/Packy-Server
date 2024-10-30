package com.dilly.mypage.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.dilly.global.IntegrationTestSupport;
import com.dilly.global.WithCustomMockUser;
import com.dilly.member.domain.Member;
import com.dilly.member.domain.ProfileImage;
import com.dilly.member.dto.request.ProfileRequest;
import com.dilly.member.dto.response.ProfileResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MyPageServiceTest extends IntegrationTestSupport {

    @DisplayName("나의 프로필을 조회한다.")
    @Test
    @WithCustomMockUser
    void getProfile() {
        // given
        Member member = memberService.getMember();

        // when
        ProfileResponse response = myPageService.getProfile();

        // then
        assertThat(response.id()).isEqualTo(member.getId());
        assertThat(response.provider()).isEqualTo(member.getProvider().toString().toLowerCase());
        assertThat(response.nickname()).isEqualTo(member.getNickname());
        assertThat(response.imgUrl()).isEqualTo(member.getProfileImg().getImgUrl());
    }

    @Nested
    @DisplayName("나의 프로필을 수정한다.")
    class updateProfile {

        private final String newNickname = "닉네임변경";

        @DisplayName("닉네임과 프로필 이미지를 수정한다.")
        @Test
        @WithCustomMockUser
        void updateNicknameAndProfileImage() {
            // given
            ProfileRequest profileRequest = ProfileRequest.builder()
                .nickname(newNickname)
                .profileImg(2L)
                .build();

            // when
            ProfileResponse response = myPageService.updateProfile(profileRequest);

            // then
            assertThat(response.nickname()).isEqualTo(newNickname);
            assertThat(response.imgUrl()).isEqualTo("www.example2.com");
        }

        @DisplayName("닉네임만 수정한다.")
        @Test
        @WithCustomMockUser
        void updateNickname() {
            // given
            ProfileRequest profileRequest = ProfileRequest.builder()
                .nickname(newNickname)
                .build();

            // when
            ProfileResponse response = myPageService.updateProfile(profileRequest);

            // then
            assertThat(response.nickname()).isEqualTo(newNickname);
            assertThat(response.imgUrl()).isEqualTo("www.example1.com");
        }

        @DisplayName("프로필 이미지만 수정한다.")
        @Test
        @WithCustomMockUser
        void updateProfileImage() {
            // given
            Long memberId = SecurityUtil.getMemberId();
            Member member = memberReader.findById(memberId);

            ProfileRequest profileRequest = ProfileRequest.builder()
                .profileImg(2L)
                .build();

            ProfileImage profileImage = profileImageReader.findById(profileRequest.profileImg());

            // when
            ProfileResponse response = myPageService.updateProfile(profileRequest);

            // then
            assertThat(response.nickname()).isEqualTo(member.getNickname());
            assertThat(response.imgUrl()).isEqualTo(profileImage.getImgUrl());
        }
    }
}
