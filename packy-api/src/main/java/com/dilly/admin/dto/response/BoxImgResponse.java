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
    String boxNormal,
    @Schema(example = "www.example.com")
    String boxSmall,
	@Schema(example = "www.example.com")
    String boxSet,
	@Schema(example = "www.example.com")
	String boxTop,
	@Schema(example = "www.example.com")
	String boxLottie
) {

	public static BoxImgResponse from(Box box) {
        return BoxImgResponse.builder()
            .id(box.getId())
            .sequence(box.getSequence())
            .boxNormal(box.getNormalImgUrl())
            .boxSmall(box.getSmallImgUrl())
            .boxSet(box.getSetImgUrl())
            .boxTop(box.getTopImgUrl())
			.boxLottie(box.getLottieMakeUrl())
            .build();
    }
}
