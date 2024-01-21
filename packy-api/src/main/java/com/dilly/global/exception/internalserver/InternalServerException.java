package com.dilly.global.exception.internalserver;

import com.dilly.global.exception.BusinessException;
import com.dilly.global.response.ErrorCode;

public class InternalServerException extends BusinessException {

	public InternalServerException(ErrorCode errorCode) {
		super(errorCode);
	}
}
