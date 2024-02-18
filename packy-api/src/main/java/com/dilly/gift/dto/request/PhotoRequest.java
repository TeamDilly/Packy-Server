package com.dilly.gift.dto.request;

import com.dilly.global.utils.validator.CustomSize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record PhotoRequest(
	@Schema(example = "www.test.com")
	String photoUrl,
	@Schema(example = "우리 같이 놀러갔던 날 :)")
	@CustomSize(max = 20, message = "사진 속 추억은 20자 이하로 입력해주세요.")
	String description,
	@Schema(example = "1")
	Integer sequence
) {

    public static PhotoRequest of(String photoUrl, String description, Integer sequence) {
        return PhotoRequest.builder()
            .photoUrl(photoUrl)
            .description(description)
            .sequence(sequence)
            .build();
    }
}
