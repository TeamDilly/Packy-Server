package com.dilly.file;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dilly.application.FileService;
import com.dilly.dto.request.FileRequest;
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

	@Operation(summary = "Presigned URL 생성", description = """
		응답으로 받은 URL로 파일과 함께 PUT 요청을 보내 S3에 파일을 업로드해주세요. 파일 접근은 URL에서 query string을 제거해서 사용해주세요.
		"""
	)
	@GetMapping("/presigned-url/{fileName}")
	public DataResponseDto<FileRequest> getPresignedUrl(
		@PathVariable(name = "fileName") @Schema(description = "확장자명을 포함해주세요")
		String fileName) {

		return DataResponseDto.from(fileService.getPresignedUrl("images", fileName));
	}
}
