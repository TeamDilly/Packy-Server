package com.dilly.admin.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ImgResponse(
	@Schema(example = "1")
	Long id,
	@Schema(example = "1")
	Long sequence,
	@Schema(example = "www.example.com")
	String imgUrl
) {
}
