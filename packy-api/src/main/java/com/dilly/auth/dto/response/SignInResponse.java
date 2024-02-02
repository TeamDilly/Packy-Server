package com.dilly.auth.dto.response;

import com.dilly.jwt.dto.JwtResponse;
import com.dilly.member.domain.Status;
import lombok.Builder;

@Builder
public record SignInResponse(
	Status status,
	JwtResponse tokenInfo
) {
}
