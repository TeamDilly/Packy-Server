package com.dilly.gift.domain.giftbox.admin;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.dilly.gift.domain.giftbox.GiftBox;
import jakarta.persistence.Column;
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
    @Column(name = "type")
    private AdminType adminType;

    @OneToOne
    @JoinColumn(name = "gift_box_id")
    private GiftBox giftBox;
}
