package com.dilly.file;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dilly.application.FileService;
import com.dilly.global.response.DataResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "파일 관련 API")
@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
public class FileController {

	private final FileService fileService;

	@Operation(summary = "Presigned URL 생성")
	@GetMapping("/presigned-url/{fileName}")
	public DataResponseDto<Map<String, String>> getPresignedUrl(
		@PathVariable(name = "fileName") @Schema(description = "확장자명을 포함해주세요")
		String fileName) {
		return DataResponseDto.from(fileService.getPresignedUrl("images", fileName));
	}
}
