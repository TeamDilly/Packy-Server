package com.dilly.gift.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Builder
@JsonInclude(Include.NON_NULL)
public record GiftBoxResponse(
    @Schema(example = "오늘을 위한 특별한 생일 선물")
    String name,
    @Schema(example = "제이")
    String senderName,
    @Schema(example = "밀리밀리밀리")
    String receiverName,
    BoxResponse box,
    EnvelopeResponse envelope,
    @Schema(example = "생일 축하해~")
    String letterContent,
    @Schema(example = "https://www.youtube.com")
    String youtubeUrl,
    List<PhotoResponse> photos,
    List<StickerResponse> stickers,
    GiftResponse gift
) {

}
