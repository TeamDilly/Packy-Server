package com.dilly.gift.dto.response;

import lombok.Builder;

@Builder
public record StickerResponse(
    String imgUrl,
    Integer location
) {

}
