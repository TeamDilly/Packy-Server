package com.dilly.mypage.api;

import com.dilly.global.response.DataResponseDto;
import com.dilly.mypage.application.MyPageService;
import com.dilly.mypage.dto.response.ProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "마이 페이지 관련 API")
@RestController
@RequestMapping("/api/v1/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @Operation(summary = "나의 프로필 조회")
    @GetMapping("/profile")
    public DataResponseDto<ProfileResponse> getProfile() {
        return DataResponseDto.from(myPageService.getProfile());
    }
}
