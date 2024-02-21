package com.dilly.exception;

public class GiftBoxAlreadyDeletedException extends BusinessException {

    public GiftBoxAlreadyDeletedException() {
        super(ErrorCode.GIFTBOX_ALREADY_DELETED);
    }
}
