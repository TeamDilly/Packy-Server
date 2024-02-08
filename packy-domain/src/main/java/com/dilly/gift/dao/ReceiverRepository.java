package com.dilly.gift.dao;

import com.dilly.gift.domain.GiftBox;
import com.dilly.gift.domain.Receiver;
import com.dilly.member.domain.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiverRepository extends JpaRepository<Receiver, Long> {

    Boolean existsByGiftBox(GiftBox giftBox);

    Long countByGiftBox(GiftBox giftBox);

    List<Receiver> findByGiftBox(GiftBox giftBox);

    Receiver findByMemberAndGiftBox(Member member, GiftBox giftBox);
}
