package com.dilly.admin.dto.response;

import lombok.Builder;

@Builder
public record ImgResponse(
	Long id,
	String imgUrl
) {
}
