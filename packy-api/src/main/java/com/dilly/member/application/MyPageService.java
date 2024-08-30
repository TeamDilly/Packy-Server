package com.dilly.member.application;

import com.dilly.member.adaptor.MemberReader;
import com.dilly.member.adaptor.ProfileImageReader;
import com.dilly.member.domain.Member;
import com.dilly.member.dto.request.ProfileRequest;
import com.dilly.member.dto.response.ProfileResponse;
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

    private final MemberService memberService;

    private final MemberReader memberReader;
    private final ProfileImageReader profileImageReader;

    public ProfileResponse getProfile() {
        Member member = memberService.getMember();

        return ProfileResponse.from(member);
    }

    public ProfileResponse updateProfile(ProfileRequest profileRequest) {
        Member member = memberService.getMember();

        Optional.ofNullable(profileRequest.nickname())
            .ifPresent(member::updateNickname);

        Optional.ofNullable(profileRequest.profileImg())
            .map(profileImageReader::findById)
            .ifPresent(member::updateProfileImage);

        return ProfileResponse.from(member);
    }
}
