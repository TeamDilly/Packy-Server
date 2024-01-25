package com.dilly.gift.dao;

import com.dilly.gift.GiftBox;
import com.dilly.gift.MemberGiftBox;
import com.dilly.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberGiftBoxRepository extends JpaRepository<MemberGiftBox, Long> {

    MemberGiftBox findBySenderAndGiftBox(Member sender, GiftBox giftBox);
}
