package com.dilly.gift.domain.letter;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnvelopePaper {

    private String envelopeBorderColorCode;
    private Integer envelopeOpacity;

    public static EnvelopePaper of(String envelopeBorderColorCode, Integer envelopeOpacity) {
        return EnvelopePaper.builder()
            .envelopeBorderColorCode(envelopeBorderColorCode)
            .envelopeOpacity(envelopeOpacity)
            .build();
    }
}
