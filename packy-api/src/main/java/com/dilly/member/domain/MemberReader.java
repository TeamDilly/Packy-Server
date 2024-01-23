package com.dilly.member.domain;

import org.springframework.stereotype.Component;

import com.dilly.exception.entitynotfound.MemberNotFoundException;
import com.dilly.member.Member;
import com.dilly.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberReader {

	private final MemberRepository memberRepository;

	public Member findById(Long id) {
		return memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
	}
}
