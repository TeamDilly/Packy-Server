package com.dilly.gift.dto.response;

import com.dilly.gift.Box;
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

    public static BoxResponse of(Box box) {
        return BoxResponse.builder()
            .boxFull(box.getFullImgUrl())
            .boxPart(box.getPartImgUrl())
            .boxBottom(box.getBottomImgUrl())
            .build();
    }
}
