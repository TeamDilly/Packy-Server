package com.dilly.gift.dto.response;

import lombok.Builder;

@Builder
public record GiftBoxResponse(
	Long id,
	String uuid
) {
}
