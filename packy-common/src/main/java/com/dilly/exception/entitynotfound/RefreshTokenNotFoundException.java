package com.dilly.exception.entitynotfound;

import com.dilly.exception.ErrorCode;

public class RefreshTokenNotFoundException extends EntityNotFoundException {

	public RefreshTokenNotFoundException() {
		super(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
	}

	public RefreshTokenNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
