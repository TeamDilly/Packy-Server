package com.dilly.gift.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record EnvelopePaperResponse(
    @Schema(example = "ED76A5")
    String borderColorCode,
    @Schema(example = "30")
    Integer opacity
) {

    public static EnvelopePaperResponse of(String borderColorCode, Integer opacity) {
        return EnvelopePaperResponse.builder()
            .borderColorCode(borderColorCode)
            .opacity(opacity)
            .build();
    }
}
