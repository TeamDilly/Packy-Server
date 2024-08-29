package com.dilly.member.adaptor;

import com.dilly.exception.entitynotfound.MemberNotFoundException;
import com.dilly.member.MemberRepository;
import com.dilly.member.domain.Member;
import com.dilly.member.domain.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberReader {

	private final MemberRepository memberRepository;

	public Member findById(Long id) {
		return memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
	}

    public Long countByStatus(Status status) {
        return memberRepository.countByStatus(status);
    }
}
