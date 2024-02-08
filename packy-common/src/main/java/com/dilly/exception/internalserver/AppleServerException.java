package com.dilly.exception.internalserver;

import static com.dilly.exception.ErrorCode.*;

import com.dilly.exception.ErrorCode;

public class AppleServerException extends InternalServerException {

	public AppleServerException() {
		super(APPLE_SERVER_ERROR);
	}

	public AppleServerException(ErrorCode errorCode) {
		super(errorCode);
	}
}
