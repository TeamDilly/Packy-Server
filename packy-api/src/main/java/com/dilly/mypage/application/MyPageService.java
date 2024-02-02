package com.dilly.mypage.application;

import com.dilly.global.utils.SecurityUtil;
import com.dilly.member.Member;
import com.dilly.member.adaptor.MemberReader;
import com.dilly.mypage.dto.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MyPageService {

    private final MemberReader memberReader;
    public ProfileResponse getProfile() {
        Long memberId = SecurityUtil.getMemberId();
        Member member = memberReader.findById(memberId);

        return ProfileResponse.builder()
            .id(member.getId())
            .nickname(member.getNickname())
            .imgUrl(member.getProfileImg().getImgUrl())
            .build();
    }
}
