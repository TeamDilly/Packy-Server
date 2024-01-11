package com.dilly.jwt.domain;

import org.springframework.stereotype.Component;

import com.dilly.global.exception.entitynotfound.RefreshTokenNotFoundException;
import com.dilly.jwt.RefreshToken;
import com.dilly.jwt.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtReader {

	private final RefreshTokenRepository refreshTokenRepository;

	public RefreshToken findByMemberId(Long memberId) {
		return refreshTokenRepository.findById(memberId).orElseThrow(RefreshTokenNotFoundException::new);
	}
}
