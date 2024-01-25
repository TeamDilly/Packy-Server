package com.dilly.gift.domain;

import com.dilly.gift.GiftBox;
import com.dilly.gift.GiftBoxSticker;
import com.dilly.gift.Sticker;
import com.dilly.gift.dao.GiftBoxStickerRepository;
import com.dilly.gift.dto.request.StickerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GiftBoxStickerWriter {

    private final GiftBoxStickerRepository giftBoxStickerRepository;
    private final StickerReader stickerReader;

    public void save(GiftBox giftBox, StickerRequest stickerRequest) {
        Sticker sticker = stickerReader.findById(stickerRequest.id());
        giftBoxStickerRepository.save(GiftBoxSticker.builder()
            .giftBox(giftBox)
            .sticker(sticker)
            .location(stickerRequest.location())
            .build()
        );
    }
}
