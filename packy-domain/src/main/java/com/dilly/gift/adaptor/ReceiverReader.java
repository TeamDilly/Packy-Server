package com.dilly.gift.adaptor;

import com.dilly.gift.dao.ReceiverRepository;
import com.dilly.gift.domain.giftbox.GiftBox;
import com.dilly.gift.domain.receiver.Receiver;
import com.dilly.member.domain.Member;
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

    public Receiver findByMemberAndGiftBox(Member member, GiftBox giftBox) {
        return receiverRepository.findByMemberAndGiftBox(member, giftBox);
    }

    public Long countByGiftBox(GiftBox giftBox) {
        return receiverRepository.countByGiftBox(giftBox);
    }

    public Long countByMember(Member member) {
        return receiverRepository.countByMember(member);
    }
}
