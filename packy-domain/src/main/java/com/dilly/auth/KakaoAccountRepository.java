package com.dilly.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dilly.member.Member;

public interface KakaoAccountRepository extends JpaRepository<KakaoAccount, String> {

	KakaoAccount findByMember(Member member);
}
