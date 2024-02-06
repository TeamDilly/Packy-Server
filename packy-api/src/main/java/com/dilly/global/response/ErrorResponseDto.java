package com.dilly.global.response;

import com.dilly.exception.ErrorCode;

public class ErrorResponseDto extends ResponseDto {

	private ErrorResponseDto(ErrorCode errorCode) {
		super(errorCode.toString(), errorCode.getMessage());
	}

	private ErrorResponseDto(ErrorCode errorCode, Exception e) {
		super(errorCode.toString(), errorCode.getMessage(e));
	}

	private ErrorResponseDto(ErrorCode errorCode, String message) {
		super(errorCode.toString(), errorCode.getMessage() + " - " + message);
	}

	public static ErrorResponseDto from(ErrorCode errorCode) {
		return new ErrorResponseDto(errorCode);
	}

	public static ErrorResponseDto of(ErrorCode errorCode, Exception e) {
		return new ErrorResponseDto(errorCode, e);
	}

	public static ErrorResponseDto of(ErrorCode errorCode, String message) {
		return new ErrorResponseDto(errorCode, message);
	}
}
