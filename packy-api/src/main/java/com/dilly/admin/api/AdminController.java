package com.dilly.admin.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dilly.global.response.DataResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "관리자 API")
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

	@Operation(summary = "서버 상태 체크")
	@GetMapping("/health")
	public DataResponseDto<String> healthCheck() {
		String activeProfile = System.getProperty("spring.profiles.active");

		return DataResponseDto.from("This is " + activeProfile + " server!");
	}
}
