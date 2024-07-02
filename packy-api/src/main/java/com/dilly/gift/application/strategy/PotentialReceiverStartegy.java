package com.dilly.gift.application.strategy;

import com.dilly.exception.GiftBoxAccessDeniedException;
import com.dilly.gift.adaptor.ReceiverWriter;
import com.dilly.gift.domain.giftbox.GiftBox;
import com.dilly.member.domain.Member;
import org.springframework.stereotype.Component;

@Component
public class PotentialReceiverStartegy implements GiftBoxStrategy {

    private final ReceiverWriter receiverWriter;

    public PotentialReceiverStartegy(ReceiverWriter receiverWriter) {
        this.receiverWriter = receiverWriter;
    }

    @Override
    public void open(Member member, GiftBox giftBox) {
        if (giftBox.getSenderDeleted().equals(false)) receiverWriter.save(member, giftBox);
    }

    @Override
    public void delete(Member member, GiftBox giftBox) {
        throw new GiftBoxAccessDeniedException();
    }
}
