package com.dilly.auth.domain;

import org.springframework.stereotype.Component;

import com.dilly.auth.AppleAccountRepository;
import com.dilly.global.exception.alreadyexist.MemberAlreadyExistException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AppleAccountReader {

	private final AppleAccountRepository appleAccountRepository;

	public void isAppleAccountPresent(String sub) {
		if (appleAccountRepository.findById(sub).isPresent()) {
			throw new MemberAlreadyExistException();
		}
	}
}
