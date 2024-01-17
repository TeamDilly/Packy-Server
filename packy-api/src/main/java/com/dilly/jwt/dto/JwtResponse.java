package com.dilly.jwt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record JwtResponse(
	String grantType,
	String accessToken,
	String refreshToken,
	Long accessTokenExpiresIn
) {
}
