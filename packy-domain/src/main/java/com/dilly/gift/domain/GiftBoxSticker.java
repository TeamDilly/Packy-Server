package com.dilly.gift.domain;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftBoxSticker {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private GiftBox giftBox;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sticker sticker;

    private Integer location;

    public static GiftBoxSticker of(GiftBox giftBox, Sticker sticker, Integer location) {
        return GiftBoxSticker.builder()
            .giftBox(giftBox)
            .sticker(sticker)
            .location(location)
            .build();
    }
}
