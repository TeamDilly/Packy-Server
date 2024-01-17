package com.dilly.admin.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dilly.admin.application.AdminService;
import com.dilly.admin.dto.response.BoxImgResponse;
import com.dilly.admin.dto.response.ImgResponse;
import com.dilly.admin.dto.response.LetterImgResponse;
import com.dilly.admin.dto.response.MusicResponse;
import com.dilly.global.response.DataResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

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

	@Operation(summary = "메시지 디자인 조회")
	@GetMapping("/design/messages")
	public DataResponseDto<List<ImgResponse>> getMessages() {
		return DataResponseDto.from(adminService.getMessages());
	}

	@Operation(summary = "편지지 디자인 조회")
	@GetMapping("/design/letters")
	public DataResponseDto<List<LetterImgResponse>> getLetters() {
		return DataResponseDto.from(adminService.getLetters());
	}

	@Operation(summary = "패키 추천 음악 조회")
	@GetMapping("/music")
	public DataResponseDto<List<MusicResponse>> getMusics() {
		return DataResponseDto.from(adminService.getMusics());
	}
}
