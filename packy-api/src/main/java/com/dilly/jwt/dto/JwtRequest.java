package com.dilly.jwt.dto;

public record JwtRequest(
	String accessToken,
	String refreshToken
) {
}
