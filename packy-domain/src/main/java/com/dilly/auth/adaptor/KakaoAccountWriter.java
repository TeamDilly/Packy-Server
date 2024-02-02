package com.dilly.auth.adaptor;

import com.dilly.auth.KakaoAccount;
import com.dilly.auth.KakaoAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoAccountWriter {

	private final KakaoAccountRepository kakaoAccountRepository;

	public void save(KakaoAccount kakaoAccount) {
		kakaoAccountRepository.save(kakaoAccount);
	}

	public void delete(KakaoAccount kakaoAccount) {
		kakaoAccountRepository.delete(kakaoAccount);
	}
}
