package com.dilly.auth.domain;

import org.springframework.stereotype.Component;

import com.dilly.auth.KakaoAccountRepository;
import com.dilly.global.exception.alreadyexist.MemberAlreadyExistException;

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
}
