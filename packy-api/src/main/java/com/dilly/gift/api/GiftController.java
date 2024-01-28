package com.dilly.gift.api;

import com.dilly.gift.application.GiftService;
import com.dilly.gift.dto.request.GiftBoxRequest;
import com.dilly.gift.dto.response.GiftBoxIdResponse;
import com.dilly.global.response.DataResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
    @PostMapping("")
    public DataResponseDto<GiftBoxIdResponse> createGiftBox(
        @RequestBody GiftBoxRequest giftBoxRequest
    ) {
        return DataResponseDto.from(giftService.createGiftBox(giftBoxRequest));
    }
}
