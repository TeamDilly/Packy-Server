package com.dilly.gift.dto.response;

import com.dilly.exception.ErrorCode;
import com.dilly.exception.UnsupportedException;
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

    public static GiftBoxesResponse of(String type, GiftBox giftBox, Receiver receiver,
        Member member) {
        switch (type) {
            case "sent" -> {
                return GiftBoxesResponse.builder()
                    .id(giftBox.getId())
                    .sender(giftBox.getSenderName())
                    .name(giftBox.getName())
                    .giftBoxDate(giftBox.getCreatedAt())
                    .boxFull(giftBox.getBox().getFullImgUrl())
                    .build();
            }
            case "received" -> {
                return GiftBoxesResponse.builder()
                    .id(giftBox.getId())
                    .sender(giftBox.getSenderName())
                    .name(giftBox.getName())
                    .giftBoxDate(receiver.getCreatedAt())
                    .boxFull(giftBox.getBox().getFullImgUrl())
                    .build();
            }
            case "all" -> {
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
            default -> throw new UnsupportedException(ErrorCode.UNSUPPORTED_GIFTBOX_TYPE);
        }
    }
}
