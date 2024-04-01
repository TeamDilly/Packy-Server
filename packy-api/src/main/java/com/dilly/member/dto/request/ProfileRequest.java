package com.dilly.member.dto.request;

import com.dilly.global.util.validator.CustomSize;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProfileRequest(
    @Schema(example = "제2")
    @CustomSize(min = 2, max = 6, message = "닉네임은 2자 이상 6자 이하로 입력해주세요")
    String nickname,
    @Schema(example = "1")
    Long profileImg
) {

}
