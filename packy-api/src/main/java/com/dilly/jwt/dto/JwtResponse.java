package com.dilly.jwt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record JwtResponse(
	@Schema(example = "1")
	Long id,
	@Schema(example = "Bearer")
	String grantType,
	@Schema(example = "eyJhbGciOiJIUzUxMiJ9...")
	String accessToken,
	@Schema(example = "eyJhbGciOiJIUzUxMiJ9...")
	String refreshToken,
	@Schema(example = "1705651413")
	Long accessTokenExpiresIn
) {
}
