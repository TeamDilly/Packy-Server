package com.dilly.gift.dto.request;

import lombok.Builder;

@Builder
public record PhotoRequest(
	String photoUrl,
	String description,
	Integer sequence
) {
}
