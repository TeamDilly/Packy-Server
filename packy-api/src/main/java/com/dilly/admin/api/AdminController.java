package com.dilly.admin.api;

import com.dilly.admin.application.AdminService;
import com.dilly.admin.dto.response.BoxImgResponse;
import com.dilly.admin.dto.response.ImgResponse;
import com.dilly.admin.dto.response.MusicResponse;
import com.dilly.global.response.DataResponseDto;
import com.dilly.global.response.SliceResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "관리자 API")
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

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
    public DataResponseDto<List<ImgResponse>> getEnvelopes() {
        return DataResponseDto.from(adminService.getEnvelopes());
    }

    @Operation(summary = "패키 추천 음악 조회")
    @GetMapping("/music")
    public DataResponseDto<List<MusicResponse>> getMusics() {
        return DataResponseDto.from(adminService.getMusics());
    }

    @Operation(summary = "스티커 조회")
    @GetMapping("/design/stickers")
    public DataResponseDto<SliceResponseDto<ImgResponse>> getStickers(
        @Parameter(hidden = true) @PageableDefault(size = 10) Pageable pageable,
        @Schema(description = "마지막 스티커 ID", example = "0")
        @RequestParam(value = "lastStickerId", required = false)
        Long lastStickerId) {
        return DataResponseDto.from(
            SliceResponseDto.from(adminService.getStickers(lastStickerId, pageable)));
    }
}
