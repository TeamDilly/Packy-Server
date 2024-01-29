package com.dilly.gift.domain;

import com.dilly.gift.GiftBox;
import com.dilly.gift.Receiver;
import com.dilly.gift.dao.ReceiverRepository;
import com.dilly.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReceiverWriter {

    private final ReceiverRepository receiverRepository;

    public void save(Member member, GiftBox giftBox) {
        receiverRepository.save(Receiver.builder()
            .receiver(member)
            .giftBox(giftBox)
            .build());
    }
}
