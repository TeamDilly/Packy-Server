package com.dilly.gift.application.strategy;

import com.dilly.exception.GiftBoxAlreadyDeletedException;
import com.dilly.gift.adaptor.ReceiverReader;
import com.dilly.gift.domain.giftbox.GiftBox;
import com.dilly.gift.domain.receiver.Receiver;
import com.dilly.gift.domain.receiver.ReceiverStatus;
import com.dilly.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ReceiverStrategy implements GiftBoxStrategy {

    private final ReceiverReader receiverReader;


    @Override
    public void open(Member member, GiftBox giftBox) {
        Receiver receiver = receiverReader.findByMemberAndGiftBox(member, giftBox);
        if (receiver.getStatus().equals(ReceiverStatus.DELETED)) {
            throw new GiftBoxAlreadyDeletedException();
        }
    }

    @Override
    public void delete(Member member, GiftBox giftBox) {
        Receiver receiver = receiverReader.findByMemberAndGiftBox(member, giftBox);
        receiver.delete();
    }
}
