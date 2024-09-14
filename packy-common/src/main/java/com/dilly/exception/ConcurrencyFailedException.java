package com.dilly.exception;

public class ConcurrencyFailedException extends BusinessException {

    public ConcurrencyFailedException() {
        super(ErrorCode.FAILED_TO_ACCESS_CONCURRENCY);
    }
}
