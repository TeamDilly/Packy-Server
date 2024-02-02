package com.dilly.auth.adaptor;

import com.dilly.auth.KakaoAccount;
import com.dilly.auth.KakaoAccountRepository;
import com.dilly.exception.alreadyexist.MemberAlreadyExistException;
import com.dilly.member.Member;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoAccountReader {

	private final KakaoAccountRepository kakaoAccountRepository;

	public void isKakaoAccountPresent(String id) {
		if (kakaoAccountRepository.findById(id).isPresent()) {
			throw new MemberAlreadyExistException();
		}
	}

	public Optional<Member> getMemberById(String id) {
		return kakaoAccountRepository.findById(id).map(KakaoAccount::getMember);
	}

	public KakaoAccount findByMember(Member member) {
		return kakaoAccountRepository.findByMember(member);
	}
}
