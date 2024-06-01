package com.dilly.gift.api;

import com.dilly.exception.ErrorCode;
import com.dilly.gift.application.GiftBoxService;
import com.dilly.gift.dto.request.DeliverStatusRequest;
import com.dilly.gift.dto.request.GiftBoxRequest;
import com.dilly.gift.dto.response.GiftBoxIdResponse;
import com.dilly.gift.dto.response.GiftBoxResponse;
import com.dilly.gift.dto.response.GiftBoxesResponse;
import com.dilly.gift.dto.response.KakaoImgResponse;
import com.dilly.gift.dto.response.MainGiftBoxResponse;
import com.dilly.gift.dto.response.WaitingGiftBoxResponse;
import com.dilly.global.response.DataResponseDto;
import com.dilly.global.response.SliceResponseDto;
import com.dilly.global.swagger.ApiErrorCodeExample;
import com.dilly.global.swagger.ApiErrorCodeExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
        giftType은 photo만 가능합니다. <br>
        gift가 없을 경우 null로 보내주세요. <br>
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
        선물이 없을 경우 응답에서 gift 객체가 제외됩니다. <br>
        이미 열린 선물박스는 다른 사람이 열 수 없습니다. <br>
        """)
    @ApiErrorCodeExamples({
        ErrorCode.GIFTBOX_NOT_FOUND,
        ErrorCode.GIFTBOX_ALREADY_OPENDED,
        ErrorCode.GIFTBOX_ACCESS_DENIED,
        ErrorCode.GIFTBOX_ALREADY_DELETED
    })
    @GetMapping("/{giftBoxId}")
    public DataResponseDto<GiftBoxResponse> openGiftBox(
        @PathVariable("giftBoxId") Long giftBoxId
    ) {
        return DataResponseDto.from(giftBoxService.openGiftBox(giftBoxId));
    }

    @Operation(summary = "(WEB) 선물박스 열기", description = """
        선물이 없을 경우 응답에서 gift 객체가 제외됩니다. <br>
        선물박스를 보낸 뒤 7일이 지나면 선물박스를 열 수 없습니다. <br>
        """)
    @ApiErrorCodeExamples({
        ErrorCode.GIFTBOX_NOT_FOUND,
        ErrorCode.GIFTBOX_ALREADY_DELETED,
        ErrorCode.GIFTBOX_URL_EXPIRED,
        ErrorCode.INVALID_INPUT_VALUE
    })
    @GetMapping("/web/{giftBoxId}")
    public DataResponseDto<GiftBoxResponse> openGiftBoxForWeb(
        @PathVariable("giftBoxId") String giftBoxId
    ) {
        return DataResponseDto.from(giftBoxService.openGiftBoxForWeb(giftBoxId));
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
        ErrorCode.GIFTBOX_ACCESS_DENIED,
        ErrorCode.FILE_DELETE_ERROR
    })
    @DeleteMapping("/{giftBoxId}")
    public DataResponseDto<String> deleteGiftBox(
        @PathVariable("giftBoxId") Long giftBoxId
    ) {
        return DataResponseDto.from(giftBoxService.deleteGiftBox(giftBoxId));
    }

    @Operation(summary = "선물박스 전송 상태 변경", description = """
        STATUS는 WAITING, DELIVERED 두 가지입니다. <br>
        선물박스 만들기 API를 호출하면 WAITING 상태로 만들어집니다. <br>
        카카오톡으로 보내기 버튼을 누를 때 해당 API를 호출하여 전송 상태를 DELIVERED로 변경합니다. <br>
        """)
    @ApiErrorCodeExamples({
        ErrorCode.GIFTBOX_NOT_FOUND,
        ErrorCode.UNSUPPORTED_DELIVER_TYPE
    })
    @PatchMapping("/{giftBoxId}")
    public DataResponseDto<String> updateDeliverStatus(
        @PathVariable("giftBoxId") Long giftBoxId,
        @RequestBody DeliverStatusRequest deliverStatusRequest
    ) {
        return DataResponseDto.from(
            giftBoxService.updateDeliverStatus(giftBoxId, deliverStatusRequest));
    }

    @Operation(summary = "보내지 않은 선물박스 조회", description = "최신순으로 6개를 조회합니다.")
    @GetMapping("/waiting")
    public DataResponseDto<List<WaitingGiftBoxResponse>> getWaitingGiftBoxes() {
        return DataResponseDto.from(giftBoxService.getWaitingGiftBoxes());
    }

    @Operation(summary = "선물박스 ID로 카카오톡 메시지 이미지 조회")
    @ApiErrorCodeExample(ErrorCode.GIFTBOX_NOT_FOUND)
    @GetMapping("/{giftBoxId}/kakao-image")
    public DataResponseDto<KakaoImgResponse> getKakaoMessageImgUrl(
        @PathVariable("giftBoxId") Long giftBoxId
    ) {
        return DataResponseDto.from(giftBoxService.getKakaoMessageImgUrl(giftBoxId));
    }

    @Operation(summary = "홈 화면에 띄울 선물박스 조회")
    @GetMapping("/main")
    public DataResponseDto<MainGiftBoxResponse> getMainGiftBox() {
        return DataResponseDto.from(giftBoxService.getMainGiftBox());
    }
}
