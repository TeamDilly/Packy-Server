package com.dilly.global.exception.internalserver;

import static com.dilly.global.response.ErrorCode.*;

import com.dilly.global.response.ErrorCode;

public class AppleServerException extends InternalServerException {

	public AppleServerException() {
		super(APPLE_SERVER_ERROR);
	}

	public AppleServerException(ErrorCode errorCode) {
		super(errorCode);
	}
}
