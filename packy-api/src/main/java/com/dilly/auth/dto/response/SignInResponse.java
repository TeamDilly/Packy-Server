package com.dilly.auth.dto.response;

import com.dilly.jwt.dto.JwtResponse;
import com.dilly.member.domain.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SignInResponse(
	@Schema(example = "NOT_REGISTERED")
	Status status,
	@Schema(example = "짱제이")
	String nickname,
	JwtResponse tokenInfo
) {

	public static SignInResponse from(Status status, JwtResponse tokenInfo) {
		return SignInResponse.builder()
			.status(status)
			.tokenInfo(tokenInfo)
			.build();
	}

	public static SignInResponse from(Status status, String nickname, JwtResponse tokenInfo) {
		return SignInResponse.builder()
			.status(status)
			.nickname(nickname)
			.tokenInfo(tokenInfo)
			.build();
	}
}
