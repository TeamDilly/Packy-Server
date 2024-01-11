package com.dilly.global.exception.entitynotfound;

import static com.dilly.global.response.ErrorCode.*;

import com.dilly.global.response.ErrorCode;

public class ProfileImageNotFoundException extends EntityNotFoundException {

	public ProfileImageNotFoundException() {
		super(PROFILE_IMAGE_NOT_FOUND);
	}

	public ProfileImageNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
