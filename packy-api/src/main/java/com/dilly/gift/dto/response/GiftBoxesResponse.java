package com.dilly.gift.dto.response;

import com.dilly.gift.domain.GiftBox;
import com.dilly.gift.domain.Receiver;
import com.dilly.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record GiftBoxesResponse(
    @Schema(example = "1")
    Long id,
    @Schema(example = "보내는사람")
    String sender,
    @Schema(example = "받는사람")
    String receiver,
    @Schema(example = "최고의선물박스")
    String name,
    @Schema(example = "2024-01-01T00:00:00")
    LocalDateTime giftBoxDate,
    @Schema(example = "www.example.com")
    String boxNormal
) {

    public static GiftBoxesResponse of(GiftBox giftBox) {
        return GiftBoxesResponse.builder()
            .id(giftBox.getId())
            .sender(giftBox.getSenderName())
            .receiver(giftBox.getReceiverName())
            .name(giftBox.getName())
            .giftBoxDate(giftBox.getCreatedAt())
            .boxNormal(giftBox.getBox().getNormalImgUrl())
            .build();
    }

    public static GiftBoxesResponse of(Receiver receiver) {
        return GiftBoxesResponse.builder()
            .id(receiver.getGiftBox().getId())
            .sender(receiver.getGiftBox().getSenderName())
            .receiver(receiver.getGiftBox().getReceiverName())
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
            .receiver(giftBox.getReceiverName())
            .name(giftBox.getName())
            .giftBoxDate(giftBoxDate)
            .boxNormal(giftBox.getBox().getNormalImgUrl())
            .build();
    }
}
