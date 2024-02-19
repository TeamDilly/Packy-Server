package com.dilly.gift.api;

import com.dilly.exception.ErrorCode;
import com.dilly.gift.application.GiftBoxService;
import com.dilly.gift.dto.request.GiftBoxRequest;
import com.dilly.gift.dto.response.GiftBoxIdResponse;
import com.dilly.gift.dto.response.GiftBoxResponse;
import com.dilly.gift.dto.response.GiftBoxesResponse;
import com.dilly.global.response.DataResponseDto;
import com.dilly.global.response.SliceResponseDto;
import com.dilly.global.swagger.ApiErrorCodeExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "선물박스 관련 API")
@RestController
@RequestMapping("/api/v1/giftboxes")
@RequiredArgsConstructor
public class GiftBoxController {

    private final GiftBoxService giftBoxService;

    @Operation(summary = "선물박스 만들기", description = """
        1. giftType은 photo만 가능합니다.
        2. gift가 없을 경우 null로 보내주세요.
        """)
    @ApiErrorCodeExamples({
        ErrorCode.BOX_NOT_FOUND,
        ErrorCode.ENVELOPE_NOT_FOUND,
        ErrorCode.STICKER_NOT_FOUND,
        ErrorCode.INVALID_INPUT_VALUE
    })
    @PostMapping("")
    public DataResponseDto<GiftBoxIdResponse> createGiftBox(
        @RequestBody @Valid GiftBoxRequest giftBoxRequest
    ) {
        return DataResponseDto.from(giftBoxService.createGiftBox(giftBoxRequest));
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
        return DataResponseDto.from(giftBoxService.openGiftBox(giftBoxId));
    }

    @Operation(summary = "주고받은 선물박스 조회")
    @Parameter(in = ParameterIn.QUERY,
        description = "한 페이지에 보여줄 선물박스 개수. 기본값은 6개",
        name = "size",
        schema = @Schema(type = "integer"))
    @GetMapping("")
    public DataResponseDto<SliceResponseDto<GiftBoxesResponse>> getGiftBoxes(
        @PageableDefault(size = 6)
        @Parameter(hidden = true)
        Pageable pageable,
        @Schema(description = "마지막 선물박스의 giftBoxDate", type = "timestamp", example = "2024-12-31T00:00:00.000")
        @RequestParam(value = "lastGiftBoxDate", required = false)
        LocalDateTime lastGiftBoxDate,
        @Schema(description = "선물박스 상태, default는 all", type = "string", allowableValues = {"sent",
            "received", "all"})
        @RequestParam(value = "type", defaultValue = "all", required = false)
        String type
    ) {
        return DataResponseDto.from(
            SliceResponseDto.from(giftBoxService.getGiftBoxes(lastGiftBoxDate, type, pageable))
        );
    }

    @Operation(summary = "선물박스 삭제")
    @ApiErrorCodeExamples({
        ErrorCode.GIFTBOX_NOT_FOUND,
        ErrorCode.GIFTBOX_ACCESS_DENIED
    })
    @DeleteMapping("/{giftBoxId}")
    public DataResponseDto<String> deleteGiftBox(
        @PathVariable("giftBoxId") Long giftBoxId
    ) {
        return DataResponseDto.from(giftBoxService.deleteGiftBox(giftBoxId));
    }
}
