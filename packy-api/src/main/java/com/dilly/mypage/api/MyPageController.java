package com.dilly.mypage.api;

import com.dilly.global.response.DataResponseDto;
import com.dilly.mypage.application.MyPageService;
import com.dilly.mypage.dto.request.ProfileRequest;
import com.dilly.mypage.dto.response.ProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "마이 페이지 관련 API")
@RestController
@RequestMapping("/api/v1/my-page")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @Operation(summary = "나의 프로필 조회")
    @GetMapping("/profile")
    public DataResponseDto<ProfileResponse> getProfile() {
        return DataResponseDto.from(myPageService.getProfile());
    }

    @Operation(summary = "나의 프로필 수정")
    @PatchMapping("/profile")
    public DataResponseDto<ProfileResponse> updateProfile(
        @RequestBody @Valid ProfileRequest profileRequest) {
        return DataResponseDto.from(myPageService.updateProfile(profileRequest));
    }
}
