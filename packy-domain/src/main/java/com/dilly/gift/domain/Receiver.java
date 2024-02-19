package com.dilly.gift.domain;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.dilly.global.BaseTimeEntity;
import com.dilly.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class Receiver extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private GiftBox giftBox;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private ReceiverStatus status = ReceiverStatus.RECEIVED;

    public static Receiver of(Member member, GiftBox giftBox) {
        return Receiver.builder()
            .member(member)
            .giftBox(giftBox)
            .build();
    }

    public void delete() {
        this.status = ReceiverStatus.DELETED;
    }
}
