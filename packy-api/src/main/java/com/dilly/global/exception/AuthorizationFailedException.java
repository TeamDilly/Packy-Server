package com.dilly.global.exception;

import com.dilly.global.response.ErrorCode;

public class AuthorizationFailedException extends BusinessException {

    public AuthorizationFailedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
