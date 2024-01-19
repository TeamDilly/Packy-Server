package com.dilly.global.exception;

import com.dilly.global.response.ErrorCode;

public class UnsupportedException extends BusinessException {

	public UnsupportedException(ErrorCode errorCode) {
		super(errorCode);
	}
}
