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
    String boxFull
) {

    public static GiftBoxesResponse of(GiftBox giftBox) {
        return GiftBoxesResponse.builder()
            .id(giftBox.getId())
            .sender(giftBox.getSenderName())
            .name(giftBox.getName())
            .giftBoxDate(giftBox.getCreatedAt())
            .boxFull(giftBox.getBox().getFullImgUrl())
            .build();
    }

    public static GiftBoxesResponse of(Receiver receiver) {
        return GiftBoxesResponse.builder()
            .id(receiver.getGiftBox().getId())
            .sender(receiver.getGiftBox().getSenderName())
            .name(receiver.getGiftBox().getName())
            .giftBoxDate(receiver.getCreatedAt())
            .boxFull(receiver.getGiftBox().getBox().getFullImgUrl())
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
            .boxFull(giftBox.getBox().getFullImgUrl())
            .build();
    }
}
