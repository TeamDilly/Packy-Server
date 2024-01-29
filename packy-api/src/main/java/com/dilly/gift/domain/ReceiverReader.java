package com.dilly.gift.domain;

import com.dilly.gift.GiftBox;
import com.dilly.gift.dao.ReceiverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReceiverReader {

    private final ReceiverRepository receiverRepository;

    public Boolean existsByGiftBoxId(GiftBox giftBox) {
        return receiverRepository.existsByGiftBox(giftBox);
    }
}
