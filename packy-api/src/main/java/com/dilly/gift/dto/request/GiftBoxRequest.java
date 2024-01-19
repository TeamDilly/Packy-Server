package com.dilly.gift.dto.request;

import java.util.List;

import lombok.Builder;

@Builder
public record GiftBoxRequest(
	Long boxId,
	Long messageId,
	Long letterPaperId,
	String letterContent,
	String youtubeUrl,
	List<PhotoRequest> photos,
	String giftType,
	String giftUrl,
	String giftMessage
) {
}
