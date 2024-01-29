package com.dilly.gift;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.dilly.global.BaseTimeEntity;
import com.dilly.member.Member;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftBox extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String uuid;

    private String name;

    private String senderName;

    private String receiverName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    private Box box;

    @ManyToOne(fetch = FetchType.LAZY)
    private Letter letter;

    @OneToMany(mappedBy = "giftBox")
    private List<Photo> photos;

    @OneToMany(mappedBy = "giftBox")
    private List<GiftBoxSticker> giftBoxStickers;

    private String youtubeUrl;

    @Embedded
    private Gift gift;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private GiftBoxType giftBoxType = GiftBoxType.PRIVATE;
}
