package com.dilly;

import com.dilly.member.domain.Member;
import com.dilly.member.domain.ProfileImage;
import com.dilly.member.domain.Provider;

public enum MemberEnumFixture {

    NORMAL_MEMBER_SENDER("보내는 사람"),
    NORMAL_MEMBER_RECEIVER("받는 사람"),
    ;

    private final String nickname;

    MemberEnumFixture(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public Member createMember(Long id) {
        return Member.builder()
            .id(id)
            .provider(Provider.TEST)
            .nickname(getNickname())
            .profileImg(createProfileImage())
            .pushNotification(true)
            .marketingAgreement(true)
            .build();
    }

    private ProfileImage createProfileImage() {
        return ProfileImage.builder()
            .id(1L)
            .imgUrl("www.test.com")
            .sequence(1L)
            .build();
    }
}
