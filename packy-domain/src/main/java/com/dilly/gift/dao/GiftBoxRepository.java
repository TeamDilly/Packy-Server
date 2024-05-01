package com.dilly.gift.dao;

import com.dilly.gift.domain.giftbox.DeliverStatus;
import com.dilly.gift.domain.giftbox.GiftBox;
import com.dilly.gift.domain.letter.Letter;
import com.dilly.member.domain.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftBoxRepository extends JpaRepository<GiftBox, Long> {
	
	GiftBox findTopByOrderByIdDesc();

    Optional<GiftBox> findByUuid(String uuid);

    GiftBox findByLetter(Letter letter);

    List<GiftBox> findTop6BySenderAndDeliverStatusAndSenderDeletedOrderByCreatedAtDesc(
        Member member,
        DeliverStatus deliverStatus, Boolean senderDeleted);
}
