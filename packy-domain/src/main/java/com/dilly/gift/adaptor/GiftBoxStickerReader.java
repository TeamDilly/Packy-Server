package com.dilly.gift.adaptor;

import com.dilly.gift.GiftBox;
import com.dilly.gift.GiftBoxSticker;
import com.dilly.gift.dao.GiftBoxStickerRepository;
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
