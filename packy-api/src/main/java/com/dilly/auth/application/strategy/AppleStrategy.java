package com.dilly.auth.application.strategy;

import com.dilly.auth.adaptor.AppleAccountReader;
import com.dilly.auth.adaptor.AppleAccountWriter;
import com.dilly.auth.application.AppleService;
import com.dilly.auth.domain.AppleAccount;
import com.dilly.auth.dto.request.SignupRequest;
import com.dilly.auth.model.AppleAccountInfo;
import com.dilly.auth.model.AppleToken;
import com.dilly.member.adaptor.MemberWriter;
import com.dilly.member.domain.Member;
import com.dilly.member.domain.ProfileImage;
import com.dilly.member.domain.Provider;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AppleStrategy implements AuthStrategy {

    private final AppleService appleService;

    private final MemberWriter memberWriter;
    private final AppleAccountReader appleAccountReader;
    private final AppleAccountWriter appleAccountWriter;

    @Override
    public Member signUp(String providerAccessToken, SignupRequest signupRequest, ProfileImage profileImage) {
        AppleToken appleToken = appleService.getAppleToken(providerAccessToken);
        AppleAccountInfo appleAccountInfo = appleService.getAppleAccountInfo(appleToken.idToken());
        appleAccountReader.isAppleAccountPresent(appleAccountInfo.sub());

        Member member = memberWriter.save(signupRequest.toMember(Provider.APPLE, profileImage));
        appleAccountWriter.save(AppleAccount.builder()
            .id(appleAccountInfo.sub())
            .member(member)
            .refreshToken(appleToken.refreshToken())
            .build()
        );

        return member;
    }

    @Override
    public Optional<Member> signIn(String providerAccessToken) {
        AppleAccountInfo appleAccountInfo = appleService.getAppleAccountInfo(
            providerAccessToken);

        return  appleAccountReader.getMemberById(appleAccountInfo.sub());
    }

    @Override
    public void withdraw(Member member) {
        AppleAccount appleAccount = appleAccountReader.findByMember(member);

        appleService.revokeAppleAccount(appleAccount);
        appleAccountWriter.delete(appleAccount);
    }
}
