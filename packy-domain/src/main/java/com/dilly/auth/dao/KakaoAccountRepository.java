package com.dilly.auth.dao;

import com.dilly.auth.domain.KakaoAccount;
import com.dilly.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KakaoAccountRepository extends JpaRepository<KakaoAccount, String> {

	KakaoAccount findByMember(Member member);
}
