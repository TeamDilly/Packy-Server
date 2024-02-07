package com.dilly.gift.dto.response;

import com.dilly.gift.domain.Box;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record BoxResponse(
    @Schema(example = "1")
    Long id,
    @Schema(example = "www.example.com")
    String boxNormal
) {

    public static BoxResponse of(Box box) {
        return BoxResponse.builder()
            .id(box.getId())
            .boxNormal(box.getNormalImgUrl())
            .build();
    }
}
