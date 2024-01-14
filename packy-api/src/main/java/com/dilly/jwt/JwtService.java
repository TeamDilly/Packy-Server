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

	public JwtResponse reissueJwt(JwtRequest jwtRequest) {
		if (!tokenProvider.validateToken(jwtRequest.refreshToken())) {
			throw new AuthorizationFailedException(ErrorCode.INVALID_REFRESH_TOKEN);
		}

		Authentication authentication = tokenProvider.getAuthentication(jwtRequest.accessToken());
		Long memberId = Long.parseLong(authentication.getName());

		RefreshToken refreshToken = jwtReader.findByMemberId(memberId);
		if (!refreshToken.getRefreshToken().equals(jwtRequest.refreshToken())) {
			throw new AuthorizationFailedException(ErrorCode.INVALID_REFRESH_TOKEN);
		}

		jwtWriter.delete(memberId);

		Member member = refreshToken.getMember();
		JwtResponse jwtResponse = tokenProvider.generateJwt(member);

		RefreshToken newRefreshToken = RefreshToken.builder()
			.refreshToken(jwtResponse.refreshToken())
			.member(member)
			.build();
		jwtWriter.save(newRefreshToken);

		return jwtResponse;
	}
}
