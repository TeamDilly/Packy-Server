package com.dilly.global.exception.entitynotfound;

import static com.dilly.global.response.ErrorCode.*;

import com.dilly.global.response.ErrorCode;

public class MemberNotFoundException extends EntityNotFoundException {

	public MemberNotFoundException() {
		super(MEMBER_NOT_FOUND);
	}

	public MemberNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
