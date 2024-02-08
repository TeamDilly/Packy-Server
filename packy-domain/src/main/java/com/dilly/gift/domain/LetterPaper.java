package com.dilly.gift.domain;

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
public class LetterPaper {

    private String letterBorderColorCode;
    private Integer letterOpacity;

    public static LetterPaper of(String letterBorderColorCode, Integer letterOpacity) {
        return LetterPaper.builder()
            .letterBorderColorCode(letterBorderColorCode)
            .letterOpacity(letterOpacity)
            .build();
    }
}
