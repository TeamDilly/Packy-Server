package com.dilly.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dilly.global.exception.AuthorizationFailedException;
import com.dilly.global.response.ErrorCode;
import com.dilly.jwt.domain.JwtReader;
import com.dilly.jwt.domain.JwtWriter;
import com.dilly.jwt.dto.JwtRequest;
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
		JwtResponse jwtResponse = tokenProvider.generateJwt(member);

		if (jwtWriter.existsByMember(member)) { // 로그인
			RefreshToken refreshToken = jwtReader.findByMember(member);
			refreshToken.update(jwtResponse.refreshToken());
		} else { // 회원가입
			RefreshToken refreshToken = RefreshToken.builder()
				.refreshToken(jwtResponse.refreshToken())
				.member(member)
				.build();
			jwtWriter.save(refreshToken);
		}

		return jwtResponse;
	}

	public JwtResponse reissueJwt(JwtRequest jwtRequest) {
		if (!tokenProvider.validateToken(jwtRequest.refreshToken())) {
			throw new AuthorizationFailedException(ErrorCode.INVALID_REFRESH_TOKEN);
		}

		Authentication authentication = tokenProvider.getAuthentication(jwtRequest.accessToken());
		Long memberId = Long.parseLong(authentication.getName());

		Member member = memberReader.findById(memberId);
		RefreshToken refreshToken = jwtReader.findByMember(member);
		if (!refreshToken.getRefreshToken().equals(jwtRequest.refreshToken())) {
			throw new AuthorizationFailedException(ErrorCode.INVALID_REFRESH_TOKEN);
		}

		JwtResponse jwtResponse = tokenProvider.generateJwt(member);

		refreshToken.update(jwtResponse.refreshToken());

		return jwtResponse;
	}
}
