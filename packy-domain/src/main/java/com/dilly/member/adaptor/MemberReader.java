package com.dilly.member.adaptor;

import com.dilly.exception.EntityNotFoundException;
import com.dilly.exception.ErrorCode;
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
		return memberRepository.findById(id).orElseThrow(
			() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND)
		);
	}

    public Long countByStatus(Status status) {
        return memberRepository.countByStatus(status);
    }
}
