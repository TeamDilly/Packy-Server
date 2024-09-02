package com.dilly.exception;

public class AuthorizationFailedException extends BusinessException {

	public AuthorizationFailedException(ErrorCode errorCode) {
		super(errorCode);
	}
}
