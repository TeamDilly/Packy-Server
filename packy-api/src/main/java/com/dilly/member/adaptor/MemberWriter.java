package com.dilly.member.adaptor;

import com.dilly.member.Member;
import com.dilly.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberWriter {

	private final MemberRepository memberRepository;

	@Transactional
	public Member save(Member member) {
		return memberRepository.save(member);
	}
}
