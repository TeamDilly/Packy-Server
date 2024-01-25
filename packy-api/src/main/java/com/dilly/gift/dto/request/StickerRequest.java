package com.dilly.gift.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record StickerRequest(
    @Schema(example = "1")
    Long id,
    @Schema(example = "1")
    Integer location
) {

}
