package com.dilly.gift.dto.response;

import com.dilly.gift.domain.GiftBox;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record GiftBoxesResponse(
    Long id,
    String sender,
    String name,
    LocalDateTime giftBoxDate,
    String boxFull
) {

    public static GiftBoxesResponse of(GiftBox giftBox) {
        return GiftBoxesResponse.builder()
            .id(giftBox.getId())
            .sender(giftBox.getSenderName())
            .name(giftBox.getName())
            .giftBoxDate(giftBox.getCreatedAt())
            .boxFull(giftBox.getBox().getFullImgUrl())
            .build();
    }
}
