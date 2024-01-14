package com.dilly.auth.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dilly.auth.application.AuthService;
import com.dilly.auth.application.KakaoService;
import com.dilly.auth.dto.request.SignupRequest;
import com.dilly.global.response.DataResponseDto;
import com.dilly.jwt.JwtService;
import com.dilly.jwt.dto.JwtRequest;
import com.dilly.jwt.dto.JwtResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "OAuth 관련 API")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	private final JwtService jwtService;
	private final KakaoService kakaoService;

	@Operation(summary = "회원 가입", description = "provider는 kakao 또는 apple")
	@PostMapping("/sign-up")
	public DataResponseDto<JwtResponse> signUp(
		@RequestHeader("Authorization") String providerAccessToken,
		@RequestBody SignupRequest signupRequest) {
		return DataResponseDto.from(authService.signUp(providerAccessToken, signupRequest));
	}

	@Operation(summary = "로그인")
	@GetMapping("/sign-in/{provider}")
	public DataResponseDto<JwtResponse> signIn(
		@PathVariable(name = "provider") String provider,
		@RequestHeader("Authorization") String providerAccessToken) {
		return DataResponseDto.from(authService.signIn(provider, providerAccessToken));
	}

	@Operation(summary = "회원 탈퇴")
	@DeleteMapping("/withdraw")
	public DataResponseDto<String> withdraw() {
		return DataResponseDto.from(authService.withdraw());
	}

	@Operation(summary = "JWT 만료 시 재발급")
	@PostMapping("/reissue")
	public DataResponseDto<JwtResponse> reissue(@RequestBody JwtRequest jwtRequest) {
		return DataResponseDto.from(jwtService.reissueJwt(jwtRequest));
	}

	@Operation(summary = "카카오 토큰으로 정보 조회")
	@GetMapping("/token/kakao/{code}")
	public DataResponseDto<String> getKakaoAccessToken(@PathVariable(name = "code") String code) {
		return DataResponseDto.from(kakaoService.getKakaoAccessToken(code));
	}
}
