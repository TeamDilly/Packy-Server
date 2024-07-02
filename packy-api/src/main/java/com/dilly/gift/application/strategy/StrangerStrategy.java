package com.dilly.gift.application.strategy;

import com.dilly.exception.GiftBoxAccessDeniedException;
import com.dilly.exception.GiftBoxAlreadyOpenedException;
import com.dilly.gift.domain.giftbox.GiftBox;
import com.dilly.member.domain.Member;
import org.springframework.stereotype.Component;

@Component
public class StrangerStrategy implements GiftBoxStrategy {

    @Override
    public void open(Member member, GiftBox giftBox) {
        throw new GiftBoxAlreadyOpenedException();
    }

    @Override
    public void delete(Member member, GiftBox giftBox) {
        throw new GiftBoxAccessDeniedException();
    }
}
