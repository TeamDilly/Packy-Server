package com.dilly.auth.application.strategy;

import com.dilly.auth.dto.request.SignupRequest;
import com.dilly.member.adaptor.MemberWriter;
import com.dilly.member.domain.Member;
import com.dilly.member.domain.ProfileImage;
import com.dilly.member.domain.Provider;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TestStrategy implements AuthStrategy {

    private final MemberWriter memberWriter;

    @Override
    public Member signUp(String providerAcessToken, SignupRequest signupRequest, ProfileImage profileImage) {
        return memberWriter.save(signupRequest.toMember(Provider.TEST, profileImage));
    }

    @Override
    public Optional<Member> signIn(String providerAccessToken) {
        return Optional.empty();
    }

    @Override
    public void withdraw(Member member) {
    }
}
