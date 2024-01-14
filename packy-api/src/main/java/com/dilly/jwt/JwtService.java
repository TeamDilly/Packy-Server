package com.dilly.jwt;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dilly.jwt.domain.JwtWriter;
import com.dilly.jwt.dto.JwtResponse;
import com.dilly.member.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class JwtService {

	private final TokenProvider tokenProvider;
	private final JwtWriter jwtWriter;

	private final RefreshTokenRepository refreshTokenRepository;

	public JwtResponse issueJwt(Member member) {
		jwtWriter.delete(member.getId());

		JwtResponse jwtResponse = tokenProvider.generateJwt(member);
		RefreshToken refreshToken = RefreshToken.builder()
			.refreshToken(jwtResponse.refreshToken())
			.member(member)
			.build();

		jwtWriter.save(refreshToken);

		return jwtResponse;
	}
}
