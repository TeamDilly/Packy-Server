package com.dilly.auth.api;

import com.dilly.auth.application.AuthService;
import com.dilly.auth.application.KakaoService;
import com.dilly.auth.dto.request.SignupRequest;
import com.dilly.auth.dto.response.SignInResponse;
import com.dilly.exception.ErrorCode;
import com.dilly.global.response.DataResponseDto;
import com.dilly.global.swagger.ApiErrorCodeExample;
import com.dilly.global.swagger.ApiErrorCodeExamples;
import com.dilly.jwt.JwtService;
import com.dilly.jwt.dto.JwtRequest;
import com.dilly.jwt.dto.JwtResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "OAuth 관련 API")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final KakaoService kakaoService;

    @Operation(summary = "회원 가입", description = "provider는 kakao 또는 apple")
    @ApiErrorCodeExamples({
        ErrorCode.UNSUPPORTED_LOGIN_TYPE,
        ErrorCode.MEMBER_ALREADY_EXIST,
        ErrorCode.KAKAO_SERVER_ERROR,
        ErrorCode.APPLE_FAILED_TO_GET_TOKEN,
        ErrorCode.APPLE_FAILED_TO_GET_PUBLIC_KEY,
        ErrorCode.APPLE_FAILED_TO_GET_INFO,
        ErrorCode.APPLE_FAILED_TO_GET_CLIENT_SECRET,
        ErrorCode.APPLE_FAILED_TO_REVOKE_ACCOUNT,
        ErrorCode.INVALID_INPUT_VALUE
    })
    @PostMapping("/sign-up")
    public DataResponseDto<JwtResponse> signUp(
        @RequestHeader("Authorization") @Schema(description = "Bearer prefix 제외해주세요") String providerAccessToken,
        @RequestBody @Valid SignupRequest signupRequest) {
        return DataResponseDto.from(authService.signUp(providerAccessToken, signupRequest));
    }

    @Operation(summary = "로그인", description = "status는 NOT_REGISTERED, REGISTERED, WITHDRAWAL, BLACKLIST")
    @ApiErrorCodeExamples({
        ErrorCode.UNSUPPORTED_LOGIN_TYPE
    })
    @GetMapping("/sign-in/{provider}")
    public DataResponseDto<SignInResponse> signIn(
        @PathVariable(name = "provider") @Schema(description = "kakao 또는 apple") String provider,
        @RequestHeader("Authorization") String providerAccessToken) {
        return DataResponseDto.from(authService.signIn(provider, providerAccessToken));
    }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("/withdraw")
    public DataResponseDto<String> withdraw() {
        return DataResponseDto.from(authService.withdraw());
    }

    @Operation(summary = "JWT 만료 시 재발급")
    @ApiErrorCodeExample(ErrorCode.INVALID_REFRESH_TOKEN)
    @PostMapping("/reissue")
    public DataResponseDto<JwtResponse> reissue(@RequestBody JwtRequest jwtRequest) {
        return DataResponseDto.from(jwtService.reissueJwt(jwtRequest));
    }

    @Operation(summary = "카카오 토큰으로 정보 조회", description = "서버에서 테스트용으로 사용하는 API입니다.")
    @GetMapping("/token/kakao/{code}")
    public DataResponseDto<String> getKakaoAccessToken(@PathVariable(name = "code") String code) {
        return DataResponseDto.from(kakaoService.getKakaoAccessToken(code));
    }
}
