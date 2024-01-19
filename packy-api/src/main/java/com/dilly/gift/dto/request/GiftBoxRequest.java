package com.dilly.gift.dto.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record GiftBoxRequest(
	@Schema(example = "1")
	Long boxId,
	@Schema(example = "1")
	Long messageId,
	@Schema(example = "1")
	Long letterPaperId,
	@Schema(example = "생일 축하해~")
	String letterContent,
	@Schema(example = "https://www.youtube.com")
	String youtubeUrl,
	List<PhotoRequest> photos,
	@Schema(example = "photo")
	String giftType,
	@Schema(example = "www.naver.com")
	String giftUrl,
	@Schema(example = "너가 가지고 싶어하던 닌텐도 스위치!")
	String giftMessage
) {
}
