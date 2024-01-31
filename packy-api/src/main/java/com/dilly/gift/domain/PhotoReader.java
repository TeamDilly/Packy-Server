package com.dilly.gift.domain;

import com.dilly.gift.GiftBox;
import com.dilly.gift.Photo;
import com.dilly.gift.dao.PhotoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhotoReader {

    private final PhotoRepository photoRepository;

    public List<Photo> findAllByGiftBox(GiftBox giftBox) {
        return photoRepository.findAllByGiftBox(giftBox);
    }
}
