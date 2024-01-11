package com.dilly.global.exception.alreadyexist;

import com.dilly.global.exception.BusinessException;
import com.dilly.global.response.ErrorCode;

public class AlreadyExistException extends BusinessException {

	public AlreadyExistException(ErrorCode errorCode) {
		super(errorCode);
	}
}
