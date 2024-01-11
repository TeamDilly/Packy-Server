package com.dilly.global.exception.entitynotfound;

import com.dilly.global.exception.BusinessException;
import com.dilly.global.response.ErrorCode;

public class EntityNotFoundException extends BusinessException {

	public EntityNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
