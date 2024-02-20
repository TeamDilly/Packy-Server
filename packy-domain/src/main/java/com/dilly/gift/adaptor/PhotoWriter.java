package com.dilly.gift.adaptor;

import com.dilly.gift.dao.PhotoRepository;
import com.dilly.gift.domain.giftbox.GiftBox;
import com.dilly.gift.domain.Photo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhotoWriter {

	private final PhotoRepository photoRepository;

	public void save(GiftBox giftBox, String photoUrl, String description, Integer sequence) {
		photoRepository.save(Photo.of(giftBox, photoUrl, description, sequence));
	}
}
