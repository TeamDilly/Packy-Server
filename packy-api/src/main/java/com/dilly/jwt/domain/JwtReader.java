package com.dilly.jwt.domain;

import org.springframework.stereotype.Component;

import com.dilly.jwt.RefreshToken;
import com.dilly.jwt.RefreshTokenRepository;
import com.dilly.member.Member;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtReader {

	private final RefreshTokenRepository refreshTokenRepository;

	public RefreshToken findByMember(Member member) {
		return refreshTokenRepository.findByMember(member);
	}
}
