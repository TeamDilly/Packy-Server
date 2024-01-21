package com.dilly.exception.alreadyexist;

import com.dilly.exception.BusinessException;
import com.dilly.exception.ErrorCode;

public class AlreadyExistException extends BusinessException {

	public AlreadyExistException(ErrorCode errorCode) {
		super(errorCode);
	}
}
