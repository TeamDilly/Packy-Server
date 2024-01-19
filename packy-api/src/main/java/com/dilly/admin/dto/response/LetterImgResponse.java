package com.dilly.admin.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record LetterImgResponse(
	@Schema(example = "1")
	Long id,
	@Schema(example = "www.example.com")
	String letterPaper,
	@Schema(example = "www.example.com")
	String envelope
) {
}
