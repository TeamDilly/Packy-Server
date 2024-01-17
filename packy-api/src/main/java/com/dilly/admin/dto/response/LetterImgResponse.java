package com.dilly.admin.dto.response;

import lombok.Builder;

@Builder
public record LetterImgResponse(
	Long id,
	String letterPaper,
	String envelope
) {
}
