package com.dilly.gift.dto.request;

import com.dilly.global.utils.validator.CustomSize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Builder;

@Builder
public record GiftBoxRequest(
    @Schema(example = "너를 위해 준비했어")
    @Size(max = 15, message = "선물박스 이름은 15자 이하로 입력해주세요.")
    String name,
    @Schema(example = "보내는 사람")
    @Size(min = 1, max = 6, message = "보내는 사람 이름은 1자 이상 6자 이하로 입력해주세요.")
    String senderName,
    @Schema(example = "받는 사람")
    @Size(min = 1, max = 6, message = "받는 사람 이름은 1자 이상 6자 이하로 입력해주세요.")
    String receiverName,
    @Schema(example = "1")
    Long boxId,
    @Schema(example = "1")
    Long envelopeId,
    @Schema(example = "생일 축하해~")
    @CustomSize(min = 1, max = 200, message = "편지 내용은 1자 이상 200자 이하로 입력해주세요.")
    String letterContent,
    @Schema(example = "https://www.youtube.com")
    String youtubeUrl,
    List<PhotoRequest> photos,
    List<StickerRequest> stickers,
    GiftRequest gift
) {

    public static GiftBoxRequest of(String name, String senderName, String receiverName, Long boxId,
        Long envelopeId, String letterContent, String youtubeUrl, List<PhotoRequest> photos,
        List<StickerRequest> stickers, GiftRequest gift) {
        return GiftBoxRequest.builder()
            .name(name)
            .senderName(senderName)
            .receiverName(receiverName)
            .boxId(boxId)
            .envelopeId(envelopeId)
            .letterContent(letterContent)
            .youtubeUrl(youtubeUrl)
            .photos(photos)
            .stickers(stickers)
            .gift(gift)
            .build();
    }

    public static GiftBoxRequest of(String name, String senderName, String receiverName, Long boxId,
        Long envelopeId, String letterContent, String youtubeUrl, List<PhotoRequest> photos,
        List<StickerRequest> stickers) {
        return GiftBoxRequest.builder()
            .name(name)
            .senderName(senderName)
            .receiverName(receiverName)
            .boxId(boxId)
            .envelopeId(envelopeId)
            .letterContent(letterContent)
            .youtubeUrl(youtubeUrl)
            .photos(photos)
            .stickers(stickers)
            .build();
    }
}
