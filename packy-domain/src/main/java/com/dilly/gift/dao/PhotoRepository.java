package com.dilly.gift.dao;

import com.dilly.gift.domain.GiftBox;
import com.dilly.gift.domain.Photo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    List<Photo> findAllByGiftBox(GiftBox giftBox);
}
