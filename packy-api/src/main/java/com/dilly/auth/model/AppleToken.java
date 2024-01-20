package com.dilly.auth.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(NON_NULL)
public record AppleToken(
	String accessToken,
	String refreshToken,
	String idToken
) {
}
