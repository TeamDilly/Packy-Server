package com.dilly.global.exception;

import com.dilly.global.response.ErrorCode;

public class InternalServerException extends BusinessException {

	public InternalServerException(ErrorCode errorCode) {
		super(errorCode);
	}
}
