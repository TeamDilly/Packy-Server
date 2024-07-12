package com.dilly.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude(NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AppleAccountInfo(
	String iss,
	String exp,
	String iat,
	String sub,
	String atHash,
	String email,
	Boolean emailVerified,
	Boolean isEmail,
	String authTime,
	Boolean nonceSupported
) {

}
