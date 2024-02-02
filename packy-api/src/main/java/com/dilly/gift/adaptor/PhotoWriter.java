package com.dilly.gift.adaptor;

import com.dilly.gift.GiftBox;
import com.dilly.gift.Photo;
import com.dilly.gift.dao.PhotoRepository;
import com.dilly.gift.dto.request.PhotoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhotoWriter {

	private final PhotoRepository photoRepository;

	public void save(GiftBox giftBox, PhotoRequest photoRequest) {
		photoRepository.save(Photo.builder()
			.giftBox(giftBox)
			.imgUrl(photoRequest.photoUrl())
			.description(photoRequest.description())
			.sequence(photoRequest.sequence())
			.build());
	}
}
