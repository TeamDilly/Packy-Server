package com.dilly.gift.dto.response;

import com.dilly.gift.domain.letter.Envelope;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record EnvelopeListResponse(
    @Schema(example = "1")
    Long id,
    @Schema(example = "1")
    Long sequence,
    EnvelopePaperResponse envelope,
    EnvelopePaperResponse letter,
    @Schema(example = "www.example.com")
    String imgUrl
) {

    public static EnvelopeListResponse from(Envelope envelope) {
        return EnvelopeListResponse.builder()
            .id(envelope.getId())
            .sequence(envelope.getSequence())
            .envelope(EnvelopePaperResponse.of(
                envelope.getEnvelopePaper().getEnvelopeBorderColorCode(),
                envelope.getEnvelopePaper().getEnvelopeOpacity()))
            .letter(EnvelopePaperResponse.of(
                envelope.getLetterPaper().getLetterBorderColorCode(),
                envelope.getLetterPaper().getLetterOpacity()))
            .imgUrl(envelope.getImgUrl())
            .build();
    }
}
