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

    // TODO: type이 received라면 giftboxDate가 Receiver의 createdAt
    // TODO: type이 없다면 내가 보낸 선물박스랑 내가 받은 선물박스를 합치고 giftboxDate로 정렬
    public static GiftBoxesResponse of(GiftBox giftBox, String type) {
        return GiftBoxesResponse.builder()
            .id(giftBox.getId())
            .sender(giftBox.getSenderName())
            .name(giftBox.getName())
            .giftBoxDate(giftBox.getCreatedAt())
            .boxFull(giftBox.getBox().getFullImgUrl())
            .build();
    }
}
