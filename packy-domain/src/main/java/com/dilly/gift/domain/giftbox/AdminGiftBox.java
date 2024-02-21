package com.dilly.gift.domain.giftbox;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.dilly.admin.domain.giftbox.ScreenType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class AdminGiftBox {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ScreenType screenType;

    @OneToOne
    @JoinColumn(name = "gift_box_id")
    private GiftBox giftBox;
}
