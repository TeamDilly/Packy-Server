package com.dilly.gift.dao;

import com.dilly.gift.domain.giftbox.GiftBox;
import com.dilly.gift.domain.receiver.Receiver;
import com.dilly.member.domain.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiverRepository extends JpaRepository<Receiver, Long> {

    Long countByGiftBox(GiftBox giftBox);

    Long countByMember(Member member);

    List<Receiver> findByGiftBox(GiftBox giftBox);

    Receiver findByMemberAndGiftBox(Member member, GiftBox giftBox);
}
