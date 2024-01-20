package com.dilly.auth.domain;

import org.springframework.stereotype.Component;

import com.dilly.auth.AppleAccount;
import com.dilly.auth.AppleAccountRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AppleAccountWriter {

	private final AppleAccountRepository appleAccountRepository;

	public void save(AppleAccount appleAccount) {
		appleAccountRepository.save(appleAccount);
	}

	public void delete(AppleAccount appleAccount) {
		appleAccountRepository.delete(appleAccount);
	}
}
