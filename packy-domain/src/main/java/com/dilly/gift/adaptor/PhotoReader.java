package com.dilly.gift.adaptor;

import com.dilly.gift.dao.PhotoRepository;
import com.dilly.gift.domain.GiftBox;
import com.dilly.gift.domain.Photo;
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
