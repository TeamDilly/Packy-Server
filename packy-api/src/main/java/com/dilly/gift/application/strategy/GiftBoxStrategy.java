package com.dilly.gift.application.strategy;

import com.dilly.gift.domain.giftbox.GiftBox;
import com.dilly.member.domain.Member;

public interface GiftBoxStrategy {

    void open(Member member, GiftBox giftBox);
    void delete(Member member, GiftBox giftBox);
}
