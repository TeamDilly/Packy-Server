package com.dilly.jwt.domain;

import org.springframework.stereotype.Component;

import com.dilly.jwt.RefreshToken;
import com.dilly.jwt.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtWriter {

	private final RefreshTokenRepository refreshTokenRepository;

	public void save(RefreshToken refreshToken) {
		refreshTokenRepository.save(refreshToken);
	}

	public void delete(Long memberId) {
		refreshTokenRepository.findById(memberId).ifPresent(refreshTokenRepository::delete);
		refreshTokenRepository.flush();
	}
}
