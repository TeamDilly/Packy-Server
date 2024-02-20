package com.dilly.gift.dto.response;

import com.dilly.gift.domain.giftbox.GiftBox;
import com.dilly.gift.domain.receiver.Receiver;
import com.dilly.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record GiftBoxesResponse(
    @Schema(example = "1")
    Long id,
    @Schema(example = "sent")
    String type,
    @Schema(example = "보내는사람")
    String sender,
    @Schema(example = "받는사람")
    String receiver,
    @Schema(example = "최고의선물박스")
    String name,
    @Schema(example = "2024-01-01T00:00:00.0000")
    LocalDateTime giftBoxDate,
    @Schema(example = "www.example.com")
    String boxNormal
) {

    public static GiftBoxesResponse of(GiftBox giftBox) {
        return GiftBoxesResponse.builder()
            .id(giftBox.getId())
            .type("sent")
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
            .type("received")
            .sender(receiver.getGiftBox().getSenderName())
            .receiver(receiver.getGiftBox().getReceiverName())
            .name(receiver.getGiftBox().getName())
            .giftBoxDate(receiver.getCreatedAt())
            .boxNormal(receiver.getGiftBox().getBox().getNormalImgUrl())
            .build();
    }

    public static GiftBoxesResponse of(GiftBox giftBox, Member member, Receiver receiver) {
        LocalDateTime giftBoxDate;
        String type;
        if (giftBox.getSender().equals(member)) {
            giftBoxDate = giftBox.getCreatedAt();
            type = "sent";
        } else {
            giftBoxDate = receiver.getCreatedAt();
            type = "received";
        }

        return GiftBoxesResponse.builder()
            .id(giftBox.getId())
            .type(type)
            .sender(giftBox.getSenderName())
            .receiver(giftBox.getReceiverName())
            .name(giftBox.getName())
            .giftBoxDate(giftBoxDate)
            .boxNormal(giftBox.getBox().getNormalImgUrl())
            .build();
    }
}
