package com.dilly.gift;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberGiftBoxRepository extends JpaRepository<MemberGiftBox, Long> {

	MemberGiftBox findByMemberIdAndGiftBoxId(Long memberId, Long giftBoxId);
}
