package com.dilly.gift.adaptor;

import com.dilly.gift.dao.GiftBoxRepository;
import com.dilly.gift.domain.Box;
import com.dilly.gift.domain.gift.Gift;
import com.dilly.gift.domain.giftbox.GiftBox;
import com.dilly.gift.domain.letter.Letter;
import com.dilly.member.domain.Member;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GiftBoxWriter {

	private final GiftBoxRepository giftBoxRepository;

	public GiftBox save(Box box, Letter letter, Gift gift, Member member,
		String name, String youtubeUrl, String senderName, String receiverName) {
		return giftBoxRepository.save(GiftBox.builder()
			.uuid(UUID.randomUUID().toString())
			.name(name)
			.sender(member)
			.box(box)
			.letter(letter)
			.youtubeUrl(youtubeUrl)
			.senderName(senderName)
			.receiverName(receiverName)
			.gift(gift)
			.build());
	}

	public GiftBox save(Box box, Letter letter, Member member,
		String name, String youtubeUrl, String senderName, String receiverName) {
		return giftBoxRepository.save(GiftBox.builder()
			.uuid(UUID.randomUUID().toString())
			.name(name)
			.sender(member)
			.box(box)
			.letter(letter)
			.youtubeUrl(youtubeUrl)
			.senderName(senderName)
			.receiverName(receiverName)
			.build());
	}

	public void delete(GiftBox giftBox) {
		giftBoxRepository.delete(giftBox);
	}
}
