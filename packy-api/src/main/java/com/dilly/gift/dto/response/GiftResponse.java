package com.dilly.gift.dto.response;

import com.dilly.gift.Gift;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record GiftResponse(
    @Schema(example = "photo")
    String type,
    @Schema(example = "www.example.com")
    String url
) {

    public static GiftResponse of(Gift gift) {
        return GiftResponse.builder()
            .type(String.valueOf(gift.getGiftType()).toLowerCase())
            .url(gift.getGiftUrl())
            .build();
    }
}
