package com.dilly.auth.application.strategy;

import com.dilly.auth.dto.request.SignupRequest;
import com.dilly.member.domain.Member;
import com.dilly.member.domain.ProfileImage;
import java.util.Optional;

public interface AuthStrategy {

    Member signUp(String providerAccessToken, SignupRequest signupRequest, ProfileImage profileImage);
    Optional<Member> signIn(String providerAccessToken);
    void withdraw(Member member);
}
