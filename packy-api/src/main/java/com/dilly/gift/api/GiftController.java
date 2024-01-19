package com.dilly.gift.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dilly.gift.application.GiftService;
import com.dilly.gift.dto.request.GiftBoxRequest;
import com.dilly.gift.dto.response.GiftBoxResponse;
import com.dilly.global.response.DataResponseDto;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "선물박스 관련 API")
@RestController
@RequestMapping("/api/v1/giftbox")
@RequiredArgsConstructor
public class GiftController {

	private final GiftService giftService;

	@PostMapping("")
	public DataResponseDto<GiftBoxResponse> createGiftBox(
		@RequestBody GiftBoxRequest giftBoxRequest
	) {
		return DataResponseDto.from(giftService.createGiftBox(giftBoxRequest));
	}
}
