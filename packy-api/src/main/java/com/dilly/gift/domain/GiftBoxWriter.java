package com.dilly.gift.domain;

import com.dilly.gift.Box;
import com.dilly.gift.Gift;
import com.dilly.gift.GiftBox;
import com.dilly.gift.Letter;
import com.dilly.gift.dao.GiftBoxRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GiftBoxWriter {

	private final GiftBoxRepository giftBoxRepository;

	public GiftBox save(Box box, Letter letter, Gift gift,
		String name, String youtubeUrl) {
		return giftBoxRepository.save(GiftBox.builder()
			.uuid(UUID.randomUUID().toString())
			.name(name)
			.box(box)
			.letter(letter)
			.youtubeUrl(youtubeUrl)
			.gift(gift)
			.build());
	}
}
