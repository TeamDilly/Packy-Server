package com.dilly.gift;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.dilly.global.BaseTimeEntity;
import com.dilly.member.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Receiver extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    private GiftBox giftBox;
}
