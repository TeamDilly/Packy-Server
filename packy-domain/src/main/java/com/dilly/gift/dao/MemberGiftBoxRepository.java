package com.dilly.gift.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dilly.gift.MemberGiftBox;

public interface MemberGiftBoxRepository extends JpaRepository<MemberGiftBox, Long> {

	MemberGiftBox findByMemberIdAndGiftBoxId(Long memberId, Long giftBoxId);
}
