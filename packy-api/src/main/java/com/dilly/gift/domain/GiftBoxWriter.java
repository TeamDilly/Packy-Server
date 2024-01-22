package com.dilly.gift.domain;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.dilly.gift.Box;
import com.dilly.gift.Gift;
import com.dilly.gift.GiftBox;
import com.dilly.gift.Letter;
import com.dilly.gift.dao.GiftBoxRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GiftBoxWriter {

	private final GiftBoxRepository giftBoxRepository;

	public GiftBox save(Box box, Letter letter, String youtubeUrl, Gift gift) {
		return giftBoxRepository.save(GiftBox.builder()
			.uuid(UUID.randomUUID().toString())
			.box(box)
			.letter(letter)
			.youtubeUrl(youtubeUrl)
			.gift(gift)
			.build());
	}
}
