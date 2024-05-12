package com.dilly.member;

import com.dilly.member.domain.Member;
import com.dilly.member.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Long countByStatus(Status status);
}
