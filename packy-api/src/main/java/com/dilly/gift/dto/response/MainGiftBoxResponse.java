package com.dilly.gift.dto.response;

import com.dilly.gift.domain.giftbox.GiftBox;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MainGiftBoxResponse(
    Long giftBoxId,
    String name,
    String senderName,
    String normalImgUrl
) {

    public static MainGiftBoxResponse from(GiftBox giftBox) {
        return MainGiftBoxResponse.builder()
            .giftBoxId(giftBox.getId())
            .name(giftBox.getName())
            .senderName(giftBox.getSenderName())
            .normalImgUrl(giftBox.getBox().getNormalImgUrl())
            .build();
    }
}
