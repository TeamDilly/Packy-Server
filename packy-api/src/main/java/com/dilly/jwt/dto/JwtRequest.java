package com.dilly.jwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record JwtRequest(
	@Schema(example = "eyJhbGciOiJIUzUxMiJ9...")
	String accessToken,
	@Schema(example = "eyJhbGciOiJIUzUxMiJ9...")
	String refreshToken
) {
}
