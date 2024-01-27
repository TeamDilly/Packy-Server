package com.dilly.mypage.dto.response;

import lombok.Builder;

@Builder
public record ProfileResponse(
    Long id,
    String nickname,
    String imgUrl
) {
}
