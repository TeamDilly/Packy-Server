package com.dilly.gift.dto.response;

import com.dilly.gift.domain.Envelope;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record EnvelopeResponse(
    @Schema(example = "www.example.com")
    String imgUrl,
    @Schema(example = "ED76A5")
    String borderColorCode
) {

    public static EnvelopeResponse of(Envelope envelope) {
        return EnvelopeResponse.builder()
            .imgUrl(envelope.getImgUrl())
            .borderColorCode(envelope.getBorderColorCode())
            .build();
    }
}
