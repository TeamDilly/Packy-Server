package com.dilly.gift.adaptor;

import com.dilly.gift.GiftBox;
import com.dilly.gift.Receiver;
import com.dilly.gift.dao.ReceiverRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReceiverReader {

    private final ReceiverRepository receiverRepository;

    public List<Receiver> findByGiftBox(GiftBox giftBox) {
        return receiverRepository.findByGiftBox(giftBox);
    }
}
