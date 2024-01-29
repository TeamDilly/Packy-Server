package com.dilly.exception;

public class GiftBoxAlreadyOpenedException extends BusinessException {

    public GiftBoxAlreadyOpenedException() {
        super(ErrorCode.GIFTBOX_ALREADY_OPENDED);
    }
}
