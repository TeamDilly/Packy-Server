package com.dilly.gift.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dilly.gift.GiftBox;
import com.dilly.gift.MemberGiftBox;
import com.dilly.gift.Photo;
import com.dilly.gift.dto.request.GiftBoxRequest;
import com.dilly.gift.dto.request.PhotoRequest;
import com.dilly.gift.dto.response.GiftBoxResponse;
import com.dilly.global.IntegrationTestSupport;
import com.dilly.global.WithCustomMockUser;
import com.dilly.global.utils.SecurityUtil;

class GiftServiceTest extends IntegrationTestSupport {

	@DisplayName("선물 박스를 만든다.")
	@Test
	@WithCustomMockUser
	void createGiftBox() {
		// given
		Long memberId = SecurityUtil.getMemberId();

		GiftBoxRequest giftBoxRequest = GiftBoxRequest.builder()
			.boxId(1L)
			.messageId(1L)
			.letterPaperId(1L)
			.letterContent("테스트")
			.youtubeUrl("www.youtube.com")
			.photos(List.of(
				PhotoRequest.builder().photoUrl("www.test1.com").description("사진 1").sequence(1).build(),
				PhotoRequest.builder().photoUrl("www.test2.com").description("사진 2").sequence(2).build()
			))
			.giftType("photo")
			.giftUrl("www.test.com")
			.giftMessage("테스트 선물")
			.build();

		// when
		GiftBoxResponse giftBoxResponse = giftService.createGiftBox(giftBoxRequest);
		GiftBox giftBox = giftBoxRepository.findTopByOrderByIdDesc();
		List<Photo> photos = photoRepository.findAllByGiftBox(giftBox);
		MemberGiftBox memberGiftBox = memberGiftBoxRepository.findByMemberIdAndGiftBoxId(memberId, giftBox.getId());

		// then
		assertThat(giftBox.getBox().getId()).isEqualTo(giftBoxRequest.boxId());
		assertThat(giftBox.getMessage().getId()).isEqualTo(giftBoxRequest.messageId());
		assertThat(giftBox.getLetter().getLetterPaper().getId()).isEqualTo(giftBoxRequest.letterPaperId());
		assertThat(giftBox.getLetter().getContent()).isEqualTo(giftBoxRequest.letterContent());
		assertThat(giftBox.getYoutubeUrl()).isEqualTo(giftBoxRequest.youtubeUrl());
		assertThat(giftBox.getGift().getGiftType().name()).isEqualTo(giftBoxRequest.giftType().toUpperCase());
		assertThat(giftBox.getGift().getGiftUrl()).isEqualTo(giftBoxRequest.giftUrl());
		assertThat(giftBox.getGift().getGiftMessage()).isEqualTo(giftBoxRequest.giftMessage());
		assertThat(photos).hasSize(2)
			.extracting("imgUrl", "description", "sequence")
			.contains(tuple("www.test1.com", "사진 1", 1), tuple("www.test2.com", "사진 2", 2));
		assertThat(memberGiftBox.getMember().getId()).isEqualTo(memberId);
		assertThat(memberGiftBox.getGiftBox().getId()).isEqualTo(giftBox.getId());
		assertThat(memberGiftBox.getIsSender()).isEqualTo(true);

		assertThat(giftBoxResponse.id()).isEqualTo(giftBox.getId());
		assertTrue(isValidUUID(giftBoxResponse.uuid()));
	}

	public static boolean isValidUUID(String value) {
		final Pattern UUID_PATTERN =
			Pattern.compile(
				"^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
		return value != null && UUID_PATTERN.matcher(value).matches();
	}
}
