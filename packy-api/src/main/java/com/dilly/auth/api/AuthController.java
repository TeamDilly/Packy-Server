package com.dilly.auth.api;

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
import com.dilly.jwt.dto.JwtResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	private final JwtService jwtService;
	private final KakaoService kakaoService;

	@PostMapping("/sign-up")
	public DataResponseDto<JwtResponse> signUp(
		@RequestHeader("Authorization") String providerAccessToken,
		@RequestBody SignupRequest signupRequest) {
		return DataResponseDto.from(authService.signUp(providerAccessToken, signupRequest));
	}

	@GetMapping("/sign-in/{provider}")
	public DataResponseDto<JwtResponse> signIn(
		@PathVariable(name = "provider") String provider,
		@RequestHeader("Authorization") String providerAccessToken) {
		return DataResponseDto.from(authService.signIn(provider, providerAccessToken));
	}

	@GetMapping("/token/kakao/{code}")
	public DataResponseDto<String> getKakaoAccessToken(@PathVariable(name = "code") String code) {
		return DataResponseDto.from(kakaoService.getKakaoAccessToken(code));
	}
}
