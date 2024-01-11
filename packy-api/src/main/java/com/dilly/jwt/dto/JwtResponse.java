package com.dilly.jwt.dto;

import lombok.Builder;

@Builder
public record JwtResponse(
	String grantType,
	String accessToken,
	String refreshToken,
	Long accessTokenExpiresIn
) {
}
