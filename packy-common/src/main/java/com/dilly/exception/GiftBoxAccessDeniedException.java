package com.dilly.exception;

public class GiftBoxAccessDeniedException extends BusinessException {

    public GiftBoxAccessDeniedException() {
        super(ErrorCode.GIFTBOX_ACCESS_DENIED);
    }

}
