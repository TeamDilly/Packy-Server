package com.dilly.gift.dto.response;

import lombok.Builder;

@Builder
public record PhotoResponse(
    String photoUrl,
    String description,
    Integer sequence
) {

}
