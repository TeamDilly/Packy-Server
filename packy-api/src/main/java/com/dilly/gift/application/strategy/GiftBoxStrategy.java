package com.dilly.gift.application.strategy;

import com.dilly.gift.domain.giftbox.GiftBox;
import com.dilly.member.domain.Member;

@FunctionalInterface
public interface GiftBoxStrategy {

    void delete(Member member, GiftBox giftBox);
}
