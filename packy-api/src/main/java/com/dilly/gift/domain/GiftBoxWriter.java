package com.dilly.gift.domain;

import com.dilly.gift.Box;
import com.dilly.gift.Gift;
import com.dilly.gift.GiftBox;
import com.dilly.gift.Letter;
import com.dilly.gift.dao.GiftBoxRepository;
import com.dilly.member.Member;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GiftBoxWriter {

	private final GiftBoxRepository giftBoxRepository;

	public GiftBox save(Box box, Letter letter, Gift gift, Member member,
		String name, String youtubeUrl) {
		return giftBoxRepository.save(GiftBox.builder()
			.uuid(UUID.randomUUID().toString())
			.name(name)
			.sender(member)
			.box(box)
			.letter(letter)
			.youtubeUrl(youtubeUrl)
			.gift(gift)
			.build());
	}

	public GiftBox save(Box box, Letter letter, Member member,
		String name, String youtubeUrl) {
		return giftBoxRepository.save(GiftBox.builder()
			.uuid(UUID.randomUUID().toString())
			.name(name)
			.sender(member)
			.box(box)
			.letter(letter)
			.youtubeUrl(youtubeUrl)
			.build());
	}
}
