package com.dilly.exception.entitynotfound;

import com.dilly.exception.ErrorCode;

public class MemberNotFoundException extends EntityNotFoundException {

	public MemberNotFoundException() {
		super(ErrorCode.MEMBER_NOT_FOUND);
	}

	public MemberNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
