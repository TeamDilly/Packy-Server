package com.dilly.global.response;

import lombok.Getter;

@Getter
public class DataResponseDto<T> extends ResponseDto {

    private final T data;

    private DataResponseDto(T data) {
        super(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage());
        this.data = data;
    }

    public static <T> DataResponseDto<T> from(T data) {
        return new DataResponseDto<>(data);
    }
}
