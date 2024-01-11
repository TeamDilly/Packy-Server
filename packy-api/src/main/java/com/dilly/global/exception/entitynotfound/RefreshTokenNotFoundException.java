package com.dilly.global.exception.entitynotfound;

import static com.dilly.global.response.ErrorCode.*;

import com.dilly.global.response.ErrorCode;

public class RefreshTokenNotFoundException extends EntityNotFoundException {

	public RefreshTokenNotFoundException() {
		super(REFRESH_TOKEN_NOT_FOUND);
	}

	public RefreshTokenNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
