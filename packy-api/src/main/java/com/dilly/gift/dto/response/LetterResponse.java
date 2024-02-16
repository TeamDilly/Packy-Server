package com.dilly.gift.dto.response;

import com.dilly.gift.domain.Letter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record LetterResponse(
    @Schema(example = "1")
    Long id,
    @Schema(example = "이 편지는 영국에서 시작되어...")
    String letterContent,
    EnvelopeResponse envelope
) {

    public static LetterResponse from(Letter letter) {
        return LetterResponse.builder()
            .id(letter.getId())
            .letterContent(letter.getContent())
            .envelope(EnvelopeResponse.of(letter.getEnvelope()))
            .build();
    }
}
