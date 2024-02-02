package com.dilly.admin.dto.response;

import com.dilly.gift.domain.Box;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record BoxImgResponse(
	@Schema(example = "1")
	Long id,
	@Schema(example = "1")
	Long sequence,
	@Schema(example = "www.example.com")
	String boxFull,
	@Schema(example = "www.example.com")
	String boxPart,
	@Schema(example = "www.example.com")
	String boxBottom
) {

    public static BoxImgResponse of(Box box) {
        return BoxImgResponse.builder()
            .id(box.getId())
            .sequence(box.getSequence())
            .boxFull(box.getFullImgUrl())
            .boxPart(box.getPartImgUrl())
            .boxBottom(box.getBottomImgUrl())
            .build();
    }
}
