package com.dilly.gift.domain;

import org.springframework.stereotype.Component;

import com.dilly.gift.GiftBox;
import com.dilly.gift.MemberGiftBox;
import com.dilly.gift.MemberGiftBoxRepository;
import com.dilly.member.Member;
import com.dilly.member.domain.MemberReader;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberGiftBoxWriter {

	private final MemberGiftBoxRepository memberGiftBoxRepository;
	private final MemberReader memberReader;

	public void save(Long memberId, GiftBox giftBox) {
		Member member = memberReader.findById(memberId);
		memberGiftBoxRepository.save(MemberGiftBox.builder()
			.member(member)
			.giftBox(giftBox)
			.isSender(true)
			.build());
	}
}
