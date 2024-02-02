package com.dilly.gift.dao;

import com.dilly.gift.domain.GiftBox;
import com.dilly.gift.domain.GiftBoxSticker;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftBoxStickerRepository extends JpaRepository<GiftBoxSticker, Long> {

    List<GiftBoxSticker> findAllByGiftBox(GiftBox giftBox);
}
