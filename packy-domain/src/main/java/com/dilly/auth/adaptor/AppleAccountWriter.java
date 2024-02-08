package com.dilly.auth.adaptor;

import com.dilly.auth.dao.AppleAccountRepository;
import com.dilly.auth.domain.AppleAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
