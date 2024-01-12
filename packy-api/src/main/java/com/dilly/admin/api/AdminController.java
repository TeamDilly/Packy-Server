package com.dilly.admin.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dilly.global.response.DataResponseDto;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

	@GetMapping("/health")
	public DataResponseDto<String> healthCheck() {
		String activeProfile = System.getProperty("spring.profiles.active");

		return DataResponseDto.from("This is " + activeProfile + " server!");
	}
}
