package com.dilly.global.response;

import java.util.Optional;
import java.util.function.Predicate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Success
    OK("S000", HttpStatus.OK, "OK"),

    // Internal Server Error
    INTERNAL_ERROR("S000", HttpStatus.INTERNAL_SERVER_ERROR, "서버에 오류가 발생했습니다."),
    METHOD_NOT_ALLOWED("S001", HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP Method 요청입니다.")
    ;

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    public String getMessage(Throwable throwable) {
        return this.getMessage(this.getMessage(this.getMessage() + " - " + throwable.getMessage()));
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
            .filter(Predicate.not(String::isBlank))
            .orElse(this.getMessage());
    }
}
