package com.dilly.gift.api;

import com.dilly.exception.ErrorCode;
import com.dilly.gift.application.GiftService;
import com.dilly.gift.dto.request.GiftBoxRequest;
import com.dilly.gift.dto.response.GiftBoxIdResponse;
import com.dilly.gift.dto.response.GiftBoxResponse;
import com.dilly.global.response.DataResponseDto;
import com.dilly.global.swagger.ApiErrorCodeExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "선물박스 관련 API")
@RestController
@RequestMapping("/api/v1/giftbox")
@RequiredArgsConstructor
public class GiftController {

    private final GiftService giftService;

    @Operation(summary = "선물박스 만들기", description = """
        1. giftType은 photo만 가능합니다.
        2. gift가 없을 경우 null로 보내주세요.
        """)
    @ApiErrorCodeExamples({
        ErrorCode.BOX_NOT_FOUND,
        ErrorCode.ENVELOPE_NOT_FOUND,
        ErrorCode.STICKER_NOT_FOUND
    })
    @PostMapping("")
    public DataResponseDto<GiftBoxIdResponse> createGiftBox(
        @RequestBody GiftBoxRequest giftBoxRequest
    ) {
        return DataResponseDto.from(giftService.createGiftBox(giftBoxRequest));
    }

    @Operation(summary = "선물박스 열기", description = """
        1. 선물이 없을 경우 응답에서 gift 객체가 제외됩니다.
        2. 이미 열린 선물박스는 다른 사람이 열 수 없습니다.
        """)
    @ApiErrorCodeExamples({
        ErrorCode.GIFTBOX_NOT_FOUND,
        ErrorCode.GIFTBOX_ALREADY_OPENDED
    })
    @GetMapping("/{giftBoxId}")
    public DataResponseDto<GiftBoxResponse> openGiftBox(
        @PathVariable("giftBoxId") Long giftBoxId
    ) {
        return DataResponseDto.from(giftService.openGiftBox(giftBoxId));
    }
}
