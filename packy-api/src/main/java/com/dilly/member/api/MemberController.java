package com.dilly.member.api;

import com.dilly.global.response.DataResponseDto;
import com.dilly.member.application.MemberService;
import com.dilly.member.dto.response.StatusResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저 관련 API")
@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "앱 사용 가능 상태 확인"
        , description = "reason: NEED_UPDATE(업데이트 필요), INVALID_STATUS(탈퇴, 정지 등 올바르지 않은 유저 상태)")
    @GetMapping("/status")
    public DataResponseDto<StatusResponse> getStatus(
        @RequestParam("app-version") @Schema(example = "1.0.0") String appVersion
    ) {
        return DataResponseDto.from(memberService.getStatus(appVersion));
    }
}
