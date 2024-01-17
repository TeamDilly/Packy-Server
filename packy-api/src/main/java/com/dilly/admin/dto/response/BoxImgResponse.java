package com.dilly.admin.dto.response;

import lombok.Builder;

@Builder
public record BoxImgResponse(
	Long id,
	String boxTop,
	String boxBottom
) {
}
