package com.dilly.exception.entitynotfound;

import com.dilly.exception.BusinessException;
import com.dilly.exception.ErrorCode;

public class EntityNotFoundException extends BusinessException {

	public EntityNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
