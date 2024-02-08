package com.dilly.exception.alreadyexist;

import com.dilly.exception.ErrorCode;

public class MemberAlreadyExistException extends AlreadyExistException {

	public MemberAlreadyExistException() {
		super(ErrorCode.MEMBER_ALREADY_EXIST);
	}

	public MemberAlreadyExistException(ErrorCode errorCode) {
		super(errorCode);
	}
}
