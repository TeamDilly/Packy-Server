package com.dilly.gift.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record GiftRequest(
    @Schema(example = "photo")
    String type,
    @Schema(example = "www.example.com")
    String url
) {

    public static GiftRequest of(String type, String url) {
        return GiftRequest.builder()
            .type(type)
            .url(url)
            .build();
    }
}
