package com.dilly.gift.adaptor;

import com.dilly.gift.dao.GiftBoxStickerRepository;
import com.dilly.gift.domain.giftbox.GiftBox;
import com.dilly.gift.domain.sticker.GiftBoxSticker;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GiftBoxStickerReader {

    private final GiftBoxStickerRepository giftBoxStickerRepository;

    public List<GiftBoxSticker> findAllByGiftBox(GiftBox giftBox) {
        return giftBoxStickerRepository.findAllByGiftBox(giftBox);
    }
}
