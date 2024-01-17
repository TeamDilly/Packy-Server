package com.dilly.auth.domain;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.dilly.auth.KakaoAccount;
import com.dilly.auth.KakaoAccountRepository;
import com.dilly.global.exception.alreadyexist.MemberAlreadyExistException;
import com.dilly.member.Member;

import lombok.RequiredArgsConstructor;

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
