package com.dilly.gift.adaptor;

import com.dilly.gift.GiftBox;
import com.dilly.gift.GiftBoxSticker;
import com.dilly.gift.Sticker;
import com.dilly.gift.dao.GiftBoxStickerRepository;
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
