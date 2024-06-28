package com.dilly.auth.application.strategy;

import com.dilly.auth.adaptor.KakaoAccountReader;
import com.dilly.auth.adaptor.KakaoAccountWriter;
import com.dilly.auth.application.KakaoService;
import com.dilly.auth.domain.KakaoAccount;
import com.dilly.auth.dto.request.SignupRequest;
import com.dilly.auth.model.KakaoResource;
import com.dilly.member.adaptor.MemberWriter;
import com.dilly.member.domain.Member;
import com.dilly.member.domain.ProfileImage;
import com.dilly.member.domain.Provider;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KakaoStrategy implements AuthStrategy {

    private final KakaoService kakaoService;

    private final MemberWriter memberWriter;
    private final KakaoAccountReader kakaoAccountReader;
    private final KakaoAccountWriter KakaoAccountWriter;
    private final KakaoAccountWriter kakaoAccountWriter;

    @Override
    public Member signUp(String providerAccessToken, SignupRequest signupRequest, ProfileImage profileImage) {

        KakaoResource kakaoResource = kakaoService.getKaKaoAccount(providerAccessToken);
        kakaoAccountReader.isKakaoAccountPresent(kakaoResource.getId());

        Member member = memberWriter.save(signupRequest.toMember(Provider.KAKAO, profileImage));
        KakaoAccountWriter.save(kakaoResource.toMember(member));

        return member;
    }

    @Override
    public Optional<Member> signIn(String providerAccessToken) {
        KakaoResource kakaoResource = kakaoService.getKaKaoAccount(providerAccessToken);

        return kakaoAccountReader.getMemberById(kakaoResource.getId());
    }

    @Override
    public void withdraw(Member member) {
        KakaoAccount kakaoAccount = kakaoAccountReader.findByMember(member);

        kakaoService.unlinkKakaoAccount(kakaoAccount);
        kakaoAccountWriter.delete(kakaoAccount);
    }
}
