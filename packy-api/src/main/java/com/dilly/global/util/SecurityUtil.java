package com.dilly.global.util;

import com.dilly.exception.AuthorizationFailedException;
import com.dilly.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtil {

	public static Long getMemberId() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || authentication.getName() == null || authentication.getName()
			.equals("anonymousUser")) {
			throw new AuthorizationFailedException(ErrorCode.AUTH_INFO_NOT_FOUND);
		}

		return Long.parseLong(authentication.getName());
	}
}
