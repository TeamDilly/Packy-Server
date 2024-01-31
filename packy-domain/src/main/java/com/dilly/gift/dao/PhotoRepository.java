package com.dilly.gift.dao;

import com.dilly.gift.GiftBox;
import com.dilly.gift.Photo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    List<Photo> findAllByGiftBox(GiftBox giftBox);
}
