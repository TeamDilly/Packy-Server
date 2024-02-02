package com.dilly.mypage.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.dilly.global.IntegrationTestSupport;
import com.dilly.global.WithCustomMockUser;
import com.dilly.global.utils.SecurityUtil;
import com.dilly.member.domain.Member;
import com.dilly.mypage.dto.response.ProfileResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MyPageServiceTest extends IntegrationTestSupport {

    @DisplayName("나의 프로필을 조회한다.")
    @Test
    @WithCustomMockUser
    void getProfile() {
        // given
        Long memberId = SecurityUtil.getMemberId();
        Member member = memberRepository.findById(memberId).orElseThrow();

        // when
        ProfileResponse response = myPageService.getProfile();

        // then
        assertThat(response.id()).isEqualTo(member.getId());
        assertThat(response.nickname()).isEqualTo(member.getNickname());
        assertThat(response.imgUrl()).isEqualTo(member.getProfileImg().getImgUrl());
    }
}
