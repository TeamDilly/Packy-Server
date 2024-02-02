package com.dilly.gift.adaptor;

import com.dilly.gift.GiftBox;
import com.dilly.gift.Photo;
import com.dilly.gift.dao.PhotoRepository;
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
