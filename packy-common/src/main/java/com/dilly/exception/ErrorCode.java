package com.dilly.exception;

import java.util.Optional;
import java.util.function.Predicate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Success
    OK(HttpStatus.OK, "OK"),

    // Common
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 오류가 발생했습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP Method 요청입니다."),
    API_NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 API를 찾을 수 없습니다."),
    QUERY_PARAMETER_REQUIRED(HttpStatus.BAD_REQUEST, "쿼리 파라미터가 필요한 API입니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다."),
    FAILED_TO_ACCESS_CONCURRENCY(HttpStatus.INTERNAL_SERVER_ERROR, "동시 접근에 실패했습니다."),

    // Kakao
    KAKAO_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "카카오 서버 연동에 오류가 발생했습니다."),

    // Apple
    APPLE_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "애플 서버 연동에 오류가 발생했습니다."),
    APPLE_FAILED_TO_GET_TOKEN(HttpStatus.INTERNAL_SERVER_ERROR, "애플 토큰을 가져오는데 실패했습니다."),
    APPLE_FAILED_TO_GET_PUBLIC_KEY(HttpStatus.INTERNAL_SERVER_ERROR, "애플 공개키를 가져오는데 실패했습니다."),
    APPLE_FAILED_TO_GET_INFO(HttpStatus.INTERNAL_SERVER_ERROR, "애플 계정 정보를 가져오는데 실패했습니다."),
    APPLE_FAILED_TO_GET_CLIENT_SECRET(HttpStatus.INTERNAL_SERVER_ERROR,
        "애플 client_secret을 가져오는데 실패했습니다."),
    APPLE_FAILED_TO_REVOKE_ACCOUNT(HttpStatus.INTERNAL_SERVER_ERROR, "애플 계정을 해지하는데 실패했습니다."),

    // Youtube
    YOUTUBE_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "유튜브 서버 연동에 오류가 발생했습니다."),

    // File
    FILE_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일 서버 연동에 오류가 발생했습니다."),
    FILE_DELETE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일 삭제에 실패했습니다."),

    // Branch
    BRANCH_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Branch 서버 연동에 오류가 발생했습니다."),

    // Authorization
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),
    MALFORMED_JWT(HttpStatus.UNAUTHORIZED, "올바르지 않은 형식의 JWT 토큰입니다."),
    EXPIRED_JWT(HttpStatus.UNAUTHORIZED, "만료된 JWT 토큰입니다."),
    UNSUPPORTED_JWT(HttpStatus.UNAUTHORIZED, "지원하지 않는 JWT 토큰입니다."),
    ILLEGAL_JWT(HttpStatus.UNAUTHORIZED, "잘못된 JWT 토큰입니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 Refresh Token입니다."),
    AUTH_INFO_NOT_FOUND(HttpStatus.UNAUTHORIZED, "Security Context에 인증 정보가 없습니다."),

    // Not Found
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    STICKER_NOT_FOUND(HttpStatus.NOT_FOUND, "스티커를 찾을 수 없습니다."),
    GIFTBOX_NOT_FOUND(HttpStatus.NOT_FOUND, "선물박스를 찾을 수 없습니다."),
    PHOTO_NOT_FOUND(HttpStatus.NOT_FOUND, "사진을 찾을 수 없습니다."),
    LETTER_NOT_FOUND(HttpStatus.NOT_FOUND, "편지를 찾을 수 없습니다."),

    // Unsupported
    UNSUPPORTED_LOGIN_TYPE(HttpStatus.BAD_REQUEST, "지원하지 않는 로그인 타입입니다."),
    UNSUPPORTED_GIFTBOX_TYPE(HttpStatus.BAD_REQUEST, "지원하지 않는 선물박스 조회 타입입니다."),
    UNSUPPORTED_DELIVER_TYPE(HttpStatus.BAD_REQUEST, "지원하지 않는 선물박스 전송 타입입니다."),
    UNSUPPORTED_SCREEN_TYPE(HttpStatus.BAD_REQUEST, "지원하지 않는 화면 타입입니다."),

    // Member
    MEMBER_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 가입된 유저입니다."),

    // GiftBox
    BOX_NOT_FOUND(HttpStatus.NOT_FOUND, "선물 박스를 찾을 수 없습니다."),
    ENVELOPE_NOT_FOUND(HttpStatus.NOT_FOUND, "편지 봉투를 찾을 수 없습니다."),
    GIFTBOX_ALREADY_OPENED(HttpStatus.CONFLICT, "이미 열린 선물입니다."),
    GIFTBOX_ACCESS_DENIED(HttpStatus.FORBIDDEN, "선물박스에 접근할 수 없습니다."),
    GIFTBOX_ALREADY_DELETED(HttpStatus.NOT_FOUND, "이미 삭제된 선물박스입니다."),
    GIFTBOX_URL_EXPIRED(HttpStatus.BAD_REQUEST, "URL이 만료되었습니다."),

    // Version
    FAILED_TO_EXTRACT_VERSION(HttpStatus.BAD_REQUEST, "사용자 버전을 추출하는데 실패했습니다."),
    INVALID_LATEST_VERSION(HttpStatus.INTERNAL_SERVER_ERROR, "최신 버전이 올바르지 않습니다."),
    ;

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
