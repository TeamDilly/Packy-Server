package com.dilly.exception;

public class UnsupportedException extends BusinessException {

	public UnsupportedException(ErrorCode errorCode) {
		super(errorCode);
	}
}
