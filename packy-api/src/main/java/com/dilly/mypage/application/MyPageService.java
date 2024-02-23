package com.dilly.mypage.application;

import com.dilly.global.util.SecurityUtil;
import com.dilly.member.adaptor.MemberReader;
import com.dilly.member.adaptor.ProfileImageReader;
import com.dilly.member.domain.Member;
import com.dilly.mypage.dto.request.ProfileRequest;
import com.dilly.mypage.dto.response.ProfileResponse;
import java.util.Optional;
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
    private final ProfileImageReader profileImageReader;

    public ProfileResponse getProfile() {
        Long memberId = SecurityUtil.getMemberId();
        Member member = memberReader.findById(memberId);

        return ProfileResponse.from(member);
    }

    public ProfileResponse updateProfile(ProfileRequest profileRequest) {
        Long memberId = SecurityUtil.getMemberId();
        Member member = memberReader.findById(memberId);

        Optional.ofNullable(profileRequest.nickname())
            .ifPresent(member::updateNickname);

        Optional.ofNullable(profileRequest.profileImg())
            .map(profileImageReader::findById)
            .ifPresent(member::updateProfileImage);

        return ProfileResponse.from(member);
    }
}
