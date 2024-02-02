package com.dilly.auth.adaptor;

import com.dilly.auth.AppleAccount;
import com.dilly.auth.AppleAccountRepository;
import com.dilly.exception.alreadyexist.MemberAlreadyExistException;
import com.dilly.member.Member;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppleAccountReader {

	private final AppleAccountRepository appleAccountRepository;

	public void isAppleAccountPresent(String sub) {
		if (appleAccountRepository.findById(sub).isPresent()) {
			throw new MemberAlreadyExistException();
		}
	}

	public Optional<Member> getMemberById(String sub) {
		return appleAccountRepository.findById(sub).map(AppleAccount::getMember);
	}

	public AppleAccount findByMember(Member member) {
		return appleAccountRepository.findByMember(member);
	}
}
