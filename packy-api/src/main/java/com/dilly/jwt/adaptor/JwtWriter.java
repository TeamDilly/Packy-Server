package com.dilly.jwt.adaptor;

import com.dilly.jwt.RefreshToken;
import com.dilly.jwt.RefreshTokenRepository;
import com.dilly.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtWriter {

	private final RefreshTokenRepository refreshTokenRepository;

	public void save(RefreshToken refreshToken) {
		refreshTokenRepository.save(refreshToken);
	}

	public void delete(RefreshToken refreshToken) {
		refreshTokenRepository.delete(refreshToken);
	}

	public boolean existsByMember(Member member) {
		return refreshTokenRepository.existsByMember(member);
	}
}
