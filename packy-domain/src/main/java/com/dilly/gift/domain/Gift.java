package com.dilly.gift.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Gift {

    @Enumerated(EnumType.STRING)
    private GiftType giftType;
    private String giftUrl;

    public static Gift of(GiftType giftType, String giftUrl) {
        return Gift.builder()
            .giftType(giftType)
            .giftUrl(giftUrl)
            .build();
    }
}
