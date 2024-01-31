package com.dilly.gift;

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
@AllArgsConstructor
@NoArgsConstructor
public class Photo {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private GiftBox giftBox;

    private String imgUrl;

    private String description;

    private Integer sequence;

    public static Photo of(GiftBox giftBox, String imgUrl, String description, Integer sequence) {
        return Photo.builder()
            .giftBox(giftBox)
            .imgUrl(imgUrl)
            .description(description)
            .sequence(sequence)
            .build();
    }
}
