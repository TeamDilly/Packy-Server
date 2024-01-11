package com.dilly.global.exception;

import com.dilly.global.response.ErrorCode;

public class NotSupportedException extends BusinessException {

    public NotSupportedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
