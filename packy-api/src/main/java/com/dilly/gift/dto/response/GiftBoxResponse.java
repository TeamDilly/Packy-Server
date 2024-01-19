package com.dilly.gift.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record GiftBoxResponse(
	@Schema(example = "1")
	Long id,
	@Schema(example = "550e8400-e29b-41d4-a716-446655440000")
	String uuid
) {
}
