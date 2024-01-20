package com.dilly.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dilly.member.Member;

public interface AppleAccountRepository extends JpaRepository<AppleAccount, String> {

	AppleAccount findByMember(Member member);
}
