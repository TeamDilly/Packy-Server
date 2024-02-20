package com.dilly.gift.adaptor;

import com.dilly.gift.dao.GiftBoxStickerRepository;
import com.dilly.gift.domain.giftbox.GiftBox;
import com.dilly.gift.domain.sticker.GiftBoxSticker;
import com.dilly.gift.domain.sticker.Sticker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GiftBoxStickerWriter {

    private final GiftBoxStickerRepository giftBoxStickerRepository;
    private final StickerReader stickerReader;

    public void save(GiftBox giftBox, Long stickerId, Integer stickerLocation) {
        Sticker sticker = stickerReader.findById(stickerId);
        giftBoxStickerRepository.save(GiftBoxSticker.of(giftBox, sticker, stickerLocation));
    }
}
