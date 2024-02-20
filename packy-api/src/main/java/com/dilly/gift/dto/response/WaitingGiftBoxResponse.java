package com.dilly.gift.dto.response;

import com.dilly.gift.domain.giftbox.GiftBox;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record WaitingGiftBoxResponse(
    @Schema(example = "1")
    Long id,
    @Schema(example = "선물박스 이름")
    String name,
    @Schema(example = "받는 사람 이름")
    String receiverName,
    @Schema(example = "2024-01-01T00:00:00.0000")
    LocalDateTime createdAt,
    @Schema(example = "www.example.com")
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
