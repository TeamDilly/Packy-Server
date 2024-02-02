package com.dilly.gift.adaptor;

import com.dilly.exception.ErrorCode;
import com.dilly.exception.entitynotfound.EntityNotFoundException;
import com.dilly.gift.dao.GiftBoxRepository;
import com.dilly.gift.domain.GiftBox;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GiftBoxReader {

    private final GiftBoxRepository giftBoxRepository;

    public GiftBox findById(Long giftBoxId) {
        return giftBoxRepository.findById(giftBoxId)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.GIFTBOX_NOT_FOUND));
    }
}
