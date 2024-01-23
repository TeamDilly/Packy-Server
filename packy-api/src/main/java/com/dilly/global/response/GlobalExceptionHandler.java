package com.dilly.global.response;

import java.sql.SQLException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.dilly.exception.BusinessException;
import com.dilly.exception.ErrorCode;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	// 비즈니스 예외 처리
	@ExceptionHandler(BusinessException.class)
	protected ResponseEntity<Object> handleBusinessException(BusinessException e) {
		log.error(e.toString(), e);
		return handleExceptionInternal(e.getErrorCode());
	}

	// 지원하지 않는 HTTP method를 호출할 경우
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(
		HttpRequestMethodNotSupportedException e) {
		log.error("HttpRequestMethodNotSupportedException : {}", e.getMessage());
		return handleExceptionInternal(ErrorCode.METHOD_NOT_ALLOWED);
	}

	// 존재하지 않는 URI에 접근할 경우
	@ExceptionHandler(NoResourceFoundException.class)
	protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException e) {
		return handleExceptionInternal(ErrorCode.API_NOT_FOUND);
	}

	// 그 밖에 발생하는 모든 예외 처리
	@ExceptionHandler(value = {Exception.class, RuntimeException.class, SQLException.class,
		DataIntegrityViolationException.class})
	protected ResponseEntity<Object> handleException(Exception e) {
		log.error(e.toString(), e);

		return handleExceptionInternal(ErrorCode.INTERNAL_ERROR, e);
	}

	private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
		return ResponseEntity.status(errorCode.getHttpStatus())
			.body(ErrorResponseDto.from(errorCode));
	}

	private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, Exception e) {
		return ResponseEntity.status(errorCode.getHttpStatus())
			.body(ErrorResponseDto.of(errorCode, e));
	}
}
