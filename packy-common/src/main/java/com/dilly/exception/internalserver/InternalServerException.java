package com.dilly.exception.internalserver;

import com.dilly.exception.BusinessException;
import com.dilly.exception.ErrorCode;

public class InternalServerException extends BusinessException {

	public InternalServerException(ErrorCode errorCode) {
		super(errorCode);
	}
}
