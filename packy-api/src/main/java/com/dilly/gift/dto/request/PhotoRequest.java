package com.dilly.gift.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record PhotoRequest(
	@Schema(example = "www.test.com")
	String photoUrl,
	@Schema(example = "우리 같이 놀러갔던 날 :)")
	String description,
	@Schema(example = "1")
	Integer sequence
) {
}
