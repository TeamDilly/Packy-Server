package com.dilly.gift.dao;

import com.dilly.gift.GiftBox;
import com.dilly.gift.Receiver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiverRepository extends JpaRepository<Receiver, Long> {

    Boolean existsByGiftBox(GiftBox giftBox);
}
