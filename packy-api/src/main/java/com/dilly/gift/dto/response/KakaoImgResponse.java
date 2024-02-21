package com.dilly.gift.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record KakaoImgResponse(
    @Schema(example = "www.example.com")
    String kakaoMessageImgUrl
) {

        public static KakaoImgResponse from(String kakaoMessageImgUrl) {
            return KakaoImgResponse.builder()
                .kakaoMessageImgUrl(kakaoMessageImgUrl)
                .build();
        }
}
