package com.dilly.member.domain;

import com.dilly.exception.ErrorCode;
import com.dilly.exception.UnsupportedException;

public enum Provider {

	KAKAO,
	APPLE,
	TEST;

	public static Provider fromString(String provider) {
		try {
			return valueOf(provider);
		} catch (IllegalArgumentException e) {
			throw new UnsupportedException(ErrorCode.UNSUPPORTED_LOGIN_TYPE);
		}
	}
}
