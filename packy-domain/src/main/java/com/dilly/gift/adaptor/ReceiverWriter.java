package com.dilly.gift.adaptor;

import com.dilly.gift.dao.ReceiverRepository;
import com.dilly.gift.domain.giftbox.GiftBox;
import com.dilly.gift.domain.receiver.Receiver;
import com.dilly.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReceiverWriter {

    private final ReceiverRepository receiverRepository;

    public void save(Member member, GiftBox giftBox) {
        receiverRepository.save(Receiver.of(member, giftBox));
    }
}
