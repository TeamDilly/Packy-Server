package com.dilly.gift.dto.response;

import com.dilly.gift.domain.giftbox.GiftBox;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record WaitingGiftBoxResponse(
    Long id,
    String name,
    String receiverName,
    LocalDateTime createdAt,
    String smallImgUrl
) {

    public static WaitingGiftBoxResponse from(GiftBox giftBox) {
        return WaitingGiftBoxResponse.builder()
            .id(giftBox.getId())
            .name(giftBox.getName())
            .receiverName(giftBox.getReceiverName())
            .createdAt(giftBox.getCreatedAt())
            .smallImgUrl(giftBox.getBox().getSmallImgUrl())
            .build();
    }
}
