package com.dilly.gift.adaptor;

import com.dilly.gift.dao.ReceiverRepository;
import com.dilly.gift.domain.GiftBox;
import com.dilly.gift.domain.Receiver;
import com.dilly.member.domain.Member;
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
