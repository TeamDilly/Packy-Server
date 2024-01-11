package com.dilly.global.exception.alreadyexist;

import com.dilly.global.response.ErrorCode;

public class MemberAlreadyExistException extends AlreadyExistException {

	public MemberAlreadyExistException() {
		super(ErrorCode.MEMBER_ALREADY_EXIST);
	}

	public MemberAlreadyExistException(ErrorCode errorCode) {
		super(errorCode);
	}
}
