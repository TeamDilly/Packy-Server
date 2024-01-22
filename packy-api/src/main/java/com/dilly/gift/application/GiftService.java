package com.dilly.gift.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dilly.gift.Box;
import com.dilly.gift.Gift;
import com.dilly.gift.GiftBox;
import com.dilly.gift.GiftType;
import com.dilly.gift.Letter;
import com.dilly.gift.LetterPaper;
import com.dilly.gift.domain.BoxReader;
import com.dilly.gift.domain.GiftBoxWriter;
import com.dilly.gift.domain.LetterPaperReader;
import com.dilly.gift.domain.LetterWriter;
import com.dilly.gift.domain.MemberGiftBoxWriter;
import com.dilly.gift.domain.PhotoWriter;
import com.dilly.gift.dto.request.GiftBoxRequest;
import com.dilly.gift.dto.request.PhotoRequest;
import com.dilly.gift.dto.response.GiftBoxResponse;
import com.dilly.global.utils.SecurityUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GiftService {

	private final GiftBoxWriter giftBoxWriter;
	private final MemberGiftBoxWriter memberGiftBoxWriter;
	private final BoxReader boxReader;
	private final LetterPaperReader letterPaperReader;
	private final LetterWriter letterWriter;
	private final PhotoWriter photoWriter;

	public GiftBoxResponse createGiftBox(GiftBoxRequest giftBoxRequest) {
		Long memberId = SecurityUtil.getMemberId();

		Box box = boxReader.findById(giftBoxRequest.boxId());
		LetterPaper letterPaper = letterPaperReader.findById(giftBoxRequest.letterPaperId());
		Letter letter = letterWriter.save(giftBoxRequest.letterContent(), letterPaper);
		Gift gift = Gift.builder()
			.giftType(GiftType.valueOf(giftBoxRequest.giftType().toUpperCase()))
			.giftUrl(giftBoxRequest.giftUrl())
			.giftMessage(giftBoxRequest.giftMessage())
			.build();

		GiftBox giftBox = giftBoxWriter.save(box, letter, giftBoxRequest.youtubeUrl(), gift);

		for (PhotoRequest photoRequest : giftBoxRequest.photos()) {
			photoWriter.save(giftBox, photoRequest);
		}

		memberGiftBoxWriter.save(memberId, giftBox);

		return GiftBoxResponse.builder()
			.id(giftBox.getId())
			.uuid(giftBox.getUuid())
			.build();
	}
}
