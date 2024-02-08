package com.dilly.mypage.dto.response;

import com.dilly.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ProfileResponse(
    @Schema(example = "1")
    Long id,
    @Schema(example = "kakao")
    String provider,
    @Schema(example = "짱제이")
    String nickname,
    @Schema(example = "www.example.com")
    String imgUrl
) {

    public static ProfileResponse from(Member member) {
        return ProfileResponse.builder()
            .id(member.getId())
            .provider(member.getProvider().toString().toLowerCase())
            .nickname(member.getNickname())
            .imgUrl(member.getProfileImg().getImgUrl())
            .build();
    }
}
