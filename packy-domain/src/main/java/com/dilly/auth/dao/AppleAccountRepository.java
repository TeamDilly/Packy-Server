package com.dilly.auth.dao;

import com.dilly.auth.domain.AppleAccount;
import com.dilly.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppleAccountRepository extends JpaRepository<AppleAccount, String> {

	AppleAccount findByMember(Member member);
}
