package com.dilly.global.response;

public class ErrorResponseDto extends ResponseDto {

    private ErrorResponseDto(ErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getMessage());
    }

    private ErrorResponseDto(ErrorCode errorCode, Exception e) {
        super(errorCode.getCode(), errorCode.getMessage(e));
    }

    public static ErrorResponseDto from(ErrorCode errorCode) {
        return new ErrorResponseDto(errorCode);
    }

    public static ErrorResponseDto of(ErrorCode errorCode, Exception e) {
        return new ErrorResponseDto(errorCode, e);
    }
}
