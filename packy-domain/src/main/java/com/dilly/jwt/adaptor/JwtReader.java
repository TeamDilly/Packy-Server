package com.dilly.jwt.adaptor;

import com.dilly.jwt.RefreshToken;
import com.dilly.jwt.RefreshTokenRepository;
import com.dilly.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtReader {

	private final RefreshTokenRepository refreshTokenRepository;

	public RefreshToken findByMember(Member member) {
		return refreshTokenRepository.findByMember(member);
	}
}
