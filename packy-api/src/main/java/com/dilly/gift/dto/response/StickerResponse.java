package com.dilly.gift.dto.response;

import com.dilly.gift.domain.sticker.GiftBoxSticker;
import lombok.Builder;

@Builder
public record StickerResponse(
    String imgUrl,
    Integer location
) {

    public static StickerResponse from(GiftBoxSticker giftBoxSticker) {
        return StickerResponse.builder()
            .imgUrl(giftBoxSticker.getSticker().getImgUrl())
            .location(giftBoxSticker.getLocation())
            .build();
    }
}
