package com.dilly.gift.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record GiftResponse(
    @Schema(example = "photo")
    String type,
    @Schema(example = "www.example.com")
    String url
) {

}
