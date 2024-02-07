package com.dilly.gift.dto.response;

import com.dilly.gift.domain.GiftBox;
import com.dilly.gift.domain.Receiver;
import com.dilly.member.domain.Member;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record GiftBoxesResponse(
    Long id,
    String sender,
    String name,
    LocalDateTime giftBoxDate,
    String boxNormal
) {

    public static GiftBoxesResponse of(GiftBox giftBox) {
        return GiftBoxesResponse.builder()
            .id(giftBox.getId())
            .sender(giftBox.getSenderName())
            .name(giftBox.getName())
            .giftBoxDate(giftBox.getCreatedAt())
            .boxNormal(giftBox.getBox().getNormalImgUrl())
            .build();
    }

    public static GiftBoxesResponse of(Receiver receiver) {
        return GiftBoxesResponse.builder()
            .id(receiver.getGiftBox().getId())
            .sender(receiver.getGiftBox().getSenderName())
            .name(receiver.getGiftBox().getName())
            .giftBoxDate(receiver.getCreatedAt())
            .boxNormal(receiver.getGiftBox().getBox().getNormalImgUrl())
            .build();
    }

    public static GiftBoxesResponse of(GiftBox giftBox, Member member, Receiver receiver) {
        LocalDateTime giftBoxDate;
        if (giftBox.getSender().equals(member)) {
            giftBoxDate = giftBox.getCreatedAt();
        } else {
            giftBoxDate = receiver.getCreatedAt();
        }

        return GiftBoxesResponse.builder()
            .id(giftBox.getId())
            .sender(giftBox.getSenderName())
            .name(giftBox.getName())
            .giftBoxDate(giftBoxDate)
            .boxNormal(giftBox.getBox().getNormalImgUrl())
            .build();
    }
}
