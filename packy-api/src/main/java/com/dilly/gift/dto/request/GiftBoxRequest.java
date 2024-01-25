package com.dilly.gift.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Builder
public record GiftBoxRequest(
    @Schema(example = "오늘을 위한 특별한 생일 선물")
    String name,
    @Schema(example = "제이(보내는 사람)")
    String senderName,
    @Schema(example = "제삼(받는 사람)")
    String receiverName,
    @Schema(example = "1")
    Long boxId,
    @Schema(example = "1")
    Long envelopeId,
    @Schema(example = "생일 축하해~")
    String letterContent,
    @Schema(example = "https://www.youtube.com")
    String youtubeUrl,
    List<PhotoRequest> photos,
    GiftRequest gift,
    List<StickerRequest> stickers
) {

}
