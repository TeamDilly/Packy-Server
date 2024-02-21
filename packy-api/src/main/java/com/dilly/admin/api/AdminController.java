package com.dilly.admin.api;

import com.dilly.admin.application.AdminService;
import com.dilly.admin.dto.response.BoxImgResponse;
import com.dilly.admin.dto.response.ImgResponse;
import com.dilly.admin.dto.response.MusicResponse;
import com.dilly.admin.dto.response.SettingResponse;
import com.dilly.application.YoutubeService;
import com.dilly.dto.response.StatusResponse;
import com.dilly.exception.ErrorCode;
import com.dilly.gift.dto.response.EnvelopeListResponse;
import com.dilly.gift.dto.response.GiftBoxResponse;
import com.dilly.global.response.DataResponseDto;
import com.dilly.global.response.SliceResponseDto;
import com.dilly.global.swagger.ApiErrorCodeExample;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "관리자 API")
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final YoutubeService youtubeService;

    @Operation(summary = "서버 상태 체크")
    @GetMapping("/health")
    public DataResponseDto<String> healthCheck() {
        String activeProfile = System.getProperty("spring.profiles.active");

        return DataResponseDto.from("This is " + activeProfile + " server!");
    }

    @Operation(summary = "프로필 이미지 조회")
    @GetMapping("/design/profiles")
    public DataResponseDto<List<ImgResponse>> getProfiles() {
        return DataResponseDto.from(adminService.getProfiles());
    }

    @Operation(summary = "박스 디자인 조회")
    @GetMapping("/design/boxes")
    public DataResponseDto<List<BoxImgResponse>> getBoxes() {
        return DataResponseDto.from(adminService.getBoxes());
    }

    @Operation(summary = "편지 봉투 디자인 조회")
    @GetMapping("/design/envelopes")
    public DataResponseDto<List<EnvelopeListResponse>> getEnvelopes() {
        return DataResponseDto.from(adminService.getEnvelopes());
    }

    @Operation(summary = "패키 추천 음악 조회")
    @GetMapping("/music")
    public DataResponseDto<List<MusicResponse>> getMusics() {
        return DataResponseDto.from(adminService.getMusics());
    }

    @Operation(summary = "스티커 조회")
    @Parameter(in = ParameterIn.QUERY
        , description = "한 페이지에 보여줄 스티커 개수. 기본값은 10개"
        , name = "size"
        , schema = @Schema(type = "integer"))
    @GetMapping("/design/stickers")
    public DataResponseDto<SliceResponseDto<ImgResponse>> getStickers(
        @PageableDefault(size = 10)
        @Parameter(hidden = true)
        Pageable pageable,
        @Schema(description = "마지막 스티커 ID", type = "integer")
        @RequestParam(value = "lastStickerId", required = false)
        Long lastStickerId) {
        return DataResponseDto.from(
            SliceResponseDto.from(adminService.getStickers(lastStickerId, pageable)));
    }

    @Operation(summary = "유튜브 링크 유효성 검사")
    @ApiErrorCodeExample(ErrorCode.YOUTUBE_SERVER_ERROR)
    @GetMapping("/youtube")
    public DataResponseDto<StatusResponse> validateYoutubeUrl(
        @Schema(description = "유튜브 링크", type = "string")
        @RequestParam(value = "url")
        String url) {
        return DataResponseDto.from(youtubeService.validateYoutubeUrl(url));
    }

    @Operation(summary = "설정 링크 조회", description = """
        패키 공식 SNS: OFFICIAL_SNS <br>
        1:1 문의하기: INQUIRY <br>
        패키에게 의견 보내기: SEND_COMMENT <br>
        이용약관: TERMS_OF_USE <br>
        개인정보처리방침: PRIVACY_POLICY <br>
        """)
    @GetMapping("/settings")
    public DataResponseDto<List<SettingResponse>> getSettingUrls() {
        return DataResponseDto.from(adminService.getSettingUrls());
    }

    @Operation(summary = "패키의 선물박스 조회")
    @GetMapping("/giftboxes/{screenType}")
    public DataResponseDto<GiftBoxResponse> getPackyGiftBox(
        @PathVariable("screenType")
        @Schema(description = "사용하는 화면", type = "string", allowableValues = {"onboarding"})
        String screenType
    ) {
        return DataResponseDto.from(adminService.getPackyGiftBox(screenType));
    }
}
