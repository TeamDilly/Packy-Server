package com.dilly.jwt;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dilly.jwt.domain.JwtReader;
import com.dilly.jwt.domain.JwtWriter;
import com.dilly.jwt.dto.JwtResponse;
import com.dilly.member.Member;
import com.dilly.member.domain.MemberReader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class JwtService {

	private final TokenProvider tokenProvider;
	private final JwtReader jwtReader;
	private final JwtWriter jwtWriter;
	private final MemberReader memberReader;

	public JwtResponse issueJwt(Member member) {
		jwtWriter.delete(member.getId());

		JwtResponse jwtResponse = tokenProvider.generateJwt(member);
		RefreshToken refreshToken = RefreshToken.builder()
			.memberId(member.getId())
			.refreshToken(jwtResponse.refreshToken())
			.build();

		// TODO: save까지 잘 되지만, 로컬 Redis를 직접 확인하면 저장이 안되는 문제가 있음
		jwtWriter.save(refreshToken);

		return jwtResponse;
	}
}
