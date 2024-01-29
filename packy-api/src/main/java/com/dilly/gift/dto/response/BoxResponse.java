package com.dilly.gift.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record BoxResponse(
    @Schema(example = "www.example.com")
    String boxFull,
    @Schema(example = "www.example.com")
    String boxPart,
    @Schema(example = "www.example.com")
    String boxBottom
) {

}
