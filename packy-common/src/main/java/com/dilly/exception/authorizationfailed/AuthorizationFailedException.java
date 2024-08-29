package com.dilly.exception.authorizationfailed;

import com.dilly.exception.BusinessException;
import com.dilly.exception.ErrorCode;

public class AuthorizationFailedException extends BusinessException {

	public AuthorizationFailedException(ErrorCode errorCode) {
		super(errorCode);
	}
}
