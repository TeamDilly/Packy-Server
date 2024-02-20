package com.dilly.gift.dto.response;

import com.dilly.gift.domain.giftbox.GiftBox;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record MusicResponse(
    @Schema(example = "1")
    Long giftBoxId,
    @Schema(example = "www.example.com")
    String youtubeUrl
) {

    public static MusicResponse from(GiftBox giftBox) {
        return MusicResponse.builder()
            .giftBoxId(giftBox.getId())
            .youtubeUrl(giftBox.getYoutubeUrl())
            .build();
    }
}
